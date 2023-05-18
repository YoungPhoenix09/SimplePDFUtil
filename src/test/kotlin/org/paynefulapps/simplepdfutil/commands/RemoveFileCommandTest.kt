package org.paynefulapps.simplepdfutil.commands

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.TestingUtil
import java.lang.Exception

class RemoveFileCommandTest {
    private val pdfState = setupPDFState()

    @Test
    fun `it is able to remove a file from pdf state`() {
        val command = RemoveFileCommand(pdfState, listOf("2"))
        val newPDFState = command.execute()
        assertEquals(2, newPDFState.getState().size)
        assertEquals(pdfState.getState()[0], newPDFState.getState()[0])
        assertEquals(pdfState.getState()[2], newPDFState.getState()[1])
    }

    @Test
    fun `it is able to remove multiple files from pdf state`() {
        val command = RemoveFileCommand(pdfState, listOf("1", "3"))
        val newPDFState = command.execute()
        assertEquals(1, newPDFState.getState().size)
        assertEquals(pdfState.getState()[1], newPDFState.getState()[0])
    }

    @Test
    fun `it errors on non-numeric arguments`() {
        val command = RemoveFileCommand(pdfState, listOf("not a number"))
        val exception = assertThrows<Exception> { command.execute() }
        assertEquals(Messages.NOT_INTEGER_ERROR, exception.message)
    }

    @Test
    fun `it does not alter original state`() {
        val command = RemoveFileCommand(pdfState, listOf("1"))
        command.execute()
        assertEquals(3, pdfState.getState().size)
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
    }

    private fun setupPDFState(): PDFState {
        val pdfFile1 = TestingUtil.createPDFFile()
        val pdfFile2 = TestingUtil.createPDFFile()
        val pdfFile3 = TestingUtil.createPDFFile()
        val command = AddFileCommand(PDFState(), listOf(
            pdfFile1.filePath.toAbsolutePath().toString(),
            pdfFile2.filePath.toAbsolutePath().toString(),
            pdfFile3.filePath.toAbsolutePath().toString(),
        ))
        return command.execute()
    }
}
