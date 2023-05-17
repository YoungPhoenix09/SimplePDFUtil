package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.paynefulapps.simplepdfutil.commands.AddFileCommand
import java.lang.Exception

class AddFileCommandTest {
    @Test
    fun `it is able to add a to pdf state`() {
        val pdfFile = TestingUtil.createPDFFile()
        val pdfState = PDFState()
        val command = AddFileCommand(pdfState, arrayOf(pdfFile.filePath.toAbsolutePath().toString()))
        val newPDFState = command.execute()
        assertEquals(1, newPDFState.getState().size)
    }
    @Test
    fun `it is able to add multiple files to pdf state`() {
        val pdfFile1 = TestingUtil.createPDFFile()
        val pdfFile2 = TestingUtil.createPDFFile()
        val pdfFile3 = TestingUtil.createPDFFile()
        val pdfState = PDFState()
        val command = AddFileCommand(pdfState, arrayOf(
            pdfFile1.filePath.toAbsolutePath().toString(),
            pdfFile2.filePath.toAbsolutePath().toString(),
            pdfFile3.filePath.toAbsolutePath().toString(),
        ))
        val newPDFState = command.execute()
        assertEquals(3, newPDFState.getState().size)
    }

    @Test
    fun `it errors on invalid file paths`() {
        val command = AddFileCommand(PDFState(), arrayOf("not a file path"))
        assertThrows<Exception> { command.execute() }
    }

    @Test
    fun `it does not alter original state`() {
        val pdfState = PDFState()
        val command = AddFileCommand(pdfState, arrayOf("C:/temp/test.pdf"))
        assertThrows<Exception> { command.execute() }
        assertEquals(0, pdfState.getState().size)
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
    }
}
