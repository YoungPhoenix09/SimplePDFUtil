package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.deleteIfExists
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PDFVerifierTest {
    private val pdfVerifier = PDFVerifier()

    @Test
    fun `it returns a PDFFIle after successfully verifying`() {
        val pdf = TestingUtil.createPDFFile()

        val pdfFile = assertDoesNotThrow { pdfVerifier.verifyFile(pdf.filePath) }
        assertTrue { pdfFile.pageCount > 0 }
    }

    @Test
    fun `it handles files that do not exist`() {
        val expectedPath = Path.of("""C:\Windows\Temp\test1.pdf""")
        val exception = assertThrows<Exception> { pdfVerifier.verifyFile(expectedPath) }
        assertEquals("No file exists at path $expectedPath.", exception.message)
    }

    @Test
    fun `it checks file is pdf`() {
        val goodTempFile = Files.createTempFile("test", ".pdf")
        val badTempFile = Files.createTempFile("test", ".doc")
        val expectedExceptionMessage = "${badTempFile.fileName} is not a PDF."
        assertThrows<IOException> { pdfVerifier.verifyFile(goodTempFile) }
        val exception = assertThrows<Exception> { pdfVerifier.verifyFile(badTempFile) }
        assertEquals(expectedExceptionMessage, exception.message)
        goodTempFile.deleteIfExists()
        badTempFile.deleteIfExists()
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
    }
}
