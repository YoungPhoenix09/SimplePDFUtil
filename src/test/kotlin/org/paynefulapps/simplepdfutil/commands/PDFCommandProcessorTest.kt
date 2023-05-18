package org.paynefulapps.simplepdfutil.commands

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.TestingUtil
import java.lang.Exception
import kotlin.test.assertEquals

class PDFCommandProcessorTest {
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
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
    }
}
