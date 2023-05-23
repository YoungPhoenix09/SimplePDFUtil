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
    fun `it validates the file name given`() {
        val expectedFileNamePath = "///mergedFile.pdf"
        UserPrompter.sendStringsAsInput("1", "1", expectedFileNamePath)
        val command = ExtractCommand(pdfState, listOf("1","3"))
        val exception = assertThrows<Exception> { command.execute() }
        assertEquals(Messages.INVALID_PATH_ERROR, exception.message)
    }

    @Test
    fun `it can extract file pages`() {
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
