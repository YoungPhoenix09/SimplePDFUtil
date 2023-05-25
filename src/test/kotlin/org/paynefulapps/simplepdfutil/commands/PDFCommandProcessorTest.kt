package org.paynefulapps.simplepdfutil.commands

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.paynefulapps.simplepdfutil.*
import java.lang.Exception
import java.nio.file.Files
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.deleteRecursively
import kotlin.test.assertEquals

class PDFCommandProcessorTest {
    private val tempDir = Files.createTempDirectory(null)
    private val pdfCommandProcessor = PDFCommandProcessor()

    @Test
    fun `it errors when no arguments are found for a command`() {
        val initialPDFState = PDFState()
        val exception = assertThrows<Exception> {
            pdfCommandProcessor.processCommand(initialPDFState, "add")
        }
        assertEquals(Messages.NO_ARGUMENTS_ERROR, exception.message)
    }

    @Test
    fun `it errors on invalid commands`() {
        val initialPDFState = PDFState()
        val exception = assertThrows<Exception> {
            pdfCommandProcessor.processCommand(initialPDFState, "this is a bad command")
        }
        assertEquals(Messages.INVALID_COMMAND_ERROR, exception.message)
    }

    @Test
    fun `it can process an AddFileCommand`() {
        val initialPDFState = PDFState()
        val testPdf = TestingUtil.createPDFFile("test")
        val testPdf2 = TestingUtil.createPDFFile("test")
        val expectedState = PDFState(listOf(testPdf, testPdf2))
        val actualState = pdfCommandProcessor.processCommand(initialPDFState, "add ${testPdf.filePath}, ${testPdf2.filePath}")
        assertEquals(expectedState.getState(), actualState.getState())
    }

    @Test
    fun `it can process a RemoveFileCommand`() {
        val testPdf = TestingUtil.createPDFFile("test")
        val testPdf2 = TestingUtil.createPDFFile("test")
        val initialPDFState = PDFState(listOf(
            testPdf, testPdf2
        ))
        val expectedState = PDFState(listOf(testPdf2))
        val actualState = pdfCommandProcessor.processCommand(initialPDFState, "remove 1")
        assertEquals(expectedState.getState(), actualState.getState())
    }

    @Test
    fun `it can process an ExtractCommand`() {
        val pdfFilePath = Path(tempDir.toString(), "testFile.pdf")
        UserPrompter.sendStringsAsInput("1", "1", pdfFilePath.toString())
        val testPdf = TestingUtil.createPDFFile("test")
        val testPdf2 = TestingUtil.createPDFFile("test")
        val initialPDFState = PDFState(listOf(
            testPdf, testPdf2
        ))
        val expectedState = PDFState(listOf(
            testPdf,
            testPdf2,
            PDFFile(pdfFilePath,2)
        ))
        val actualState = pdfCommandProcessor.processCommand(initialPDFState, "extract 1,2")
        assertEquals(expectedState.getState(), actualState.getState())
    }

    @Test
    fun `it can process a MergeCommand`() {
        val pdfFilePath = Path(tempDir.toString(), "mergedFile.pdf")
        UserPrompter.sendStringsAsInput(pdfFilePath.toString())
        val testPdf = TestingUtil.createPDFFile("test")
        val testPdf2 = TestingUtil.createPDFFile("test")
        val initialPDFState = PDFState(listOf(
            testPdf, testPdf2
        ))
        val expectedState = PDFState(listOf(
            testPdf,
            testPdf2,
            PDFFile(pdfFilePath,2)
        ))
        val actualState = pdfCommandProcessor.processCommand(initialPDFState, "merge 1,2")
        assertEquals(expectedState.getState(), actualState.getState())
    }

    @Test
    fun `it can process a DeleteCommand`() {
        UserPrompter.sendStringsAsInput("1-2")
        val testPdf = TestingUtil.createPDFFile("test", 3)
        val initialPDFState = PDFState(listOf(
            testPdf
        ))
        val expectedState = PDFState(listOf(
            PDFFile(testPdf.filePath, 1),
        ))
        val actualState = pdfCommandProcessor.processCommand(initialPDFState, "delete 1")
        assertEquals(expectedState.getState(), actualState.getState())
    }

    @Test
    fun `it can process a ReorderCommand`() {
        UserPrompter.sendStringsAsInput("2,3")
        val testPdf = TestingUtil.createPDFFile("test", 3)
        val initialPDFState = PDFState(listOf(
            testPdf
        ))
        val expectedState = PDFState(listOf(
            PDFFile(testPdf.filePath, 3),
        ))
        val actualState = pdfCommandProcessor.processCommand(initialPDFState, "reorder 1")
        assertEquals(expectedState.getState(), actualState.getState())
    }

    @OptIn(ExperimentalPathApi::class)
    @AfterEach
    fun cleanup() {
        tempDir.deleteRecursively()
        TestingUtil.cleanupCreatedFiles()
    }
}
