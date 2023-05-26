package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import kotlin.io.path.name
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PDFStateTest {
    private val pdfState = PDFState()

    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it can add a file`() {
        val newState = pdfState.addFile(TestingUtil.createPDFFile("test1"))
        assertTrue(newState.getPDFFileList().isNotEmpty())
    }

    @Test
    fun `it can remove files`() {
        val test1File = TestingUtil.createPDFFile("test1")
        val stateAfterAdditions = pdfState.addFile(test1File)
            .addFile(TestingUtil.createPDFFile("test2"))
            .addFile(TestingUtil.createPDFFile("test3"))

        val stateAfterRemovals = stateAfterAdditions.removeFile(2)
            .removeFile(2)

        assertEquals(test1File.filePath.name, stateAfterRemovals.getPDFFileList()[0].filePath.name)
    }

    @Test
    fun `it errors when trying to remove a file index that doesn't exist`() {
        val expectedMessage = "There is not a file with ID number 2."
        val exception = assertThrows<Exception> { pdfState.removeFile(2) }
        assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `it can print current state`() {
        val pdfFile = TestingUtil.createPDFFile("test1")
        val newLine = System.lineSeparator()
        val expectedMessage = "${Messages.PDF_STATE_HEADER}${newLine}1. ${pdfFile.filePath.fileName} - ${pdfFile.pageCount} page(s)${newLine}${newLine}"
        val newState = pdfState.addFile(pdfFile)
        newState.printState()
        assertEquals(expectedMessage, SystemIOReplacer.newOut.toString())
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
        SystemIOReplacer.restoreSystemOutputs()
    }
}
