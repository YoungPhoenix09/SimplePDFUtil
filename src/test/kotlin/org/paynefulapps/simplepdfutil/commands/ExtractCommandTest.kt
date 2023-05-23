package org.paynefulapps.simplepdfutil.commands

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.paynefulapps.simplepdfutil.*
import java.nio.file.Files
import kotlin.io.path.*

class ExtractCommandTest {
    private val pdfState = TestingUtil.setupPDFStateWithFiles()
    private val tempDirPath = Files.createTempDirectory(null)

    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it asks for pages to merge`() {
        val pdfFilePath = Path(tempDirPath.toString(), "mergeFile.pdf")
        val expectedMessages = Messages.promptForPages(1)
            .joinWithNewLine(Messages.promptForPages(3))
            .joinWithNewLine(Messages.PROMPT_FOR_NEW_FILE_NAME)
            .joinWithNewLine("Pages were merged into new file $pdfFilePath.")
            .joinWithNewLine("")
        UserPrompter.sendStringsAsInput("1", "1", pdfFilePath.toString())
        val command = ExtractCommand(pdfState, listOf("1","3"))
        command.execute()
        assertEquals(expectedMessages, SystemIOReplacer.newOut.toString())
    }

    @Test
    fun `it handles page lists and ranges`() {
        val expectedPageList = listOf(1,2,3,5)
        val pageList = ExtractCommand(pdfState, listOf("1","3")).parsePages(" 1 - 3, 5 ")
        assertEquals(expectedPageList, pageList)
    }

    @Test
    fun `it errors on a bad page specifications`() {
        UserPrompter.sendStringsAsInput("a", "b")
        val command = ExtractCommand(pdfState, listOf("1","3"))
        val exception = assertThrows<Exception> { command.execute() }
        assertEquals(Messages.getBadPageError("a"), exception.message)
    }

    @Test
    fun `it errors on a bad page range specifications`() {
        UserPrompter.sendStringsAsInput("a - b", "b - c")
        val command = ExtractCommand(pdfState, listOf("1","3"))
        val exception = assertThrows<Exception> { command.execute() }
        assertEquals(Messages.getInvalidRangeError("a - b"), exception.message)
    }

    @Test
    fun `it errors when no files are in state`() {
        val exception = assertThrows<Exception> { ExtractCommand(PDFState(), listOf("1","3")) }
        assertEquals(Messages.NO_FILES_ERROR, exception.message)
    }

    @Test
    fun `it errors when invalid file ids are specified`() {
        val command = ExtractCommand(pdfState, listOf("4","6"))
        val exception = assertThrows<Exception> { command.execute() }
        assertEquals(Messages.getNoFileIdError(4), exception.message)
    }

    @Test
    fun `it errors when invalid pages are specified`() {
        UserPrompter.sendStringsAsInput("2")
        val command = ExtractCommand(pdfState, listOf("1","3"))
        val exception = assertThrows<Exception> { command.execute() }
        assertEquals(Messages.getInvalidPagesError(1,2), exception.message)
    }

    @Test
    fun `it asks for new file name`() {
        val pdfFilePath = Path(tempDirPath.toString(), "mergeFile.pdf")
        val expectedMessages = Messages.promptForPages(1)
            .joinWithNewLine(Messages.promptForPages(3))
            .joinWithNewLine(Messages.PROMPT_FOR_NEW_FILE_NAME)
            .joinWithNewLine("Pages were merged into new file $pdfFilePath.")
            .joinWithNewLine("")
        UserPrompter.sendStringsAsInput("1", "1", pdfFilePath.toString())
        val command = ExtractCommand(pdfState, listOf("1","3"))
        command.execute()
        assertEquals(expectedMessages, SystemIOReplacer.newOut.toString())
    }

    @Test
    fun `it validates the file name given`() {
        val expectedFileNamePath = "///mergedFile.pdf"
        UserPrompter.sendStringsAsInput("1", "1", expectedFileNamePath)
        val command = ExtractCommand(pdfState, listOf("1","3"))
        val exception = assertThrows<Exception> { command.execute() }
        assertEquals(Messages.INVALID_PATH_ERROR, exception.message)
    }

    @Test
    fun `it can merge file pages`() {
        val fileNamePath = Path(tempDirPath.toString(), "mergedFile.pdf")
        val expectedPdfFile = PDFFile(fileNamePath, 2)
        UserPrompter.sendStringsAsInput("1", "1", fileNamePath.toString())
        val command = ExtractCommand(pdfState, listOf("1","3"))
        val newPdfState = command.execute()
        assertEquals(4, newPdfState.getState().size)
        assertEquals(expectedPdfFile, newPdfState.getState()[3])
    }

    @OptIn(ExperimentalPathApi::class)
    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
        SystemIOReplacer.restoreSystemOutputs()
        tempDirPath.deleteRecursively()
    }
}
