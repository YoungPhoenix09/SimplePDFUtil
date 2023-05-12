package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import java.nio.file.Path
import kotlin.io.path.name
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PDFStateTest {
    private val pdfState = PDFState()

    @Test
    fun `it can add a file`() {
        pdfState.addFile(createPDFFile("test1"))
        assertTrue(pdfState.getState().isNotEmpty())
    }

    @Test
    fun `it can remove files`() {
        pdfState.addFile(createPDFFile("test1"))
        pdfState.addFile(createPDFFile("test2"))
        pdfState.addFile(createPDFFile("test3"))

        pdfState.removeFile(2)
        pdfState.removeFile(2)

        assertEquals("test1.pdf", pdfState.getState()[0].filePath.name)
    }

    @Test
    fun `it errors when trying to remove a file index that doesn't exist`() {
        val expectedMessage = "There is not a file with ID number 2."
        val exception = assertThrows<Exception> { pdfState.removeFile(2) }
        assertEquals(expectedMessage, exception.message)
    }



    private fun createPDFFile(name: String) = PDFFile(
        Path.of("/c/temp/$name.pdf"),
        1
    )
}
