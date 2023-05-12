package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PDFVerifierTest {
    private val pdfVerifier = PDFVerifier()

    @Test
    fun `it returns a PDFFIle after successfully verifying`() {
        val path = Path.of("""C:\Windows\Temp\test1.pdf""")
        val pdfFile = assertDoesNotThrow { pdfVerifier.verifyFile(path) }
        assertTrue { pdfFile.pageCount > 0 }
    }

    @Test
    fun `it handles files that do not exist`() {
        val expectedPath = Path.of("""C:\Windows\Temp\test1.pdf""")
        val exception = assertThrows<Exception> { pdfVerifier.verifyPDFExists(expectedPath) }
        assertEquals("No file exists at path $expectedPath.", exception.message)
    }

    @Test
    fun `it checks file is pdf`() {
        val goodPath = Path.of("""C:\Windows\Temp\test1.pdf""")
        val badPath = Path.of("""C:\Windows\Temp\test1.doc""")
        pdfVerifier.verifyFileIsPDF(goodPath)
        assertTrue { pdfVerifier.verifyFileIsPDF(goodPath) }
        assertFalse { pdfVerifier.verifyFileIsPDF(badPath) }
    }
}
