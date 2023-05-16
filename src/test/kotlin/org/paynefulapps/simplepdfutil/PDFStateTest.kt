package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import kotlin.io.path.name
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PDFStateTest {
    private val pdfState = PDFState()

    @Test
    fun `it can add a file`() {
        pdfState.addFile(TestingUtil.createPDFFile("test1"))
        assertTrue(pdfState.getState().isNotEmpty())
    }

    @Test
    fun `it can remove files`() {
        val test1File = TestingUtil.createPDFFile("test1")
        pdfState.addFile(test1File)
        pdfState.addFile(TestingUtil.createPDFFile("test2"))
        pdfState.addFile(TestingUtil.createPDFFile("test3"))

        pdfState.removeFile(2)
        pdfState.removeFile(2)

        assertEquals(test1File.filePath.name, pdfState.getState()[0].filePath.name)
    }

    @Test
    fun `it errors when trying to remove a file index that doesn't exist`() {
        val expectedMessage = "There is not a file with ID number 2."
        val exception = assertThrows<Exception> { pdfState.removeFile(2) }
        assertEquals(expectedMessage, exception.message)
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
    }
}
