package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.paynefulapps.simplepdfutil.commands.AddFileCommand

class AddFileCommandTest {
    @Test
    fun `it is able to add a to pdf state`() {
        val pdfFile = TestingUtil.createPDFFile()
        val pdfState = PDFState()
        val command = AddFileCommand(pdfState, arrayOf(pdfFile.filePath.toAbsolutePath().toString()))
        command.execute()
        assertEquals(1, pdfState.getState().size)
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
        command.execute()
        assertEquals(3, pdfState.getState().size)
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
    }
}
