package org.paynefulapps.simplepdfutil.commands

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.paynefulapps.simplepdfutil.*

class DeleteCommandTest {
    private val pdfState = setupState()

    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it can delete file pages`() {
        UserPrompter.sendStringsAsInput("2,3", "1")
        val command = DeleteCommand(pdfState, listOf("1","2"))
        val newPdfState = command.execute()
        assertEquals(3, newPdfState.getPDFFileList().size)
        assertEquals(4, newPdfState.getPDFFileList()[0].pageCount)
        assertEquals(1, newPdfState.getPDFFileList()[1].pageCount)
        assertEquals(1, newPdfState.getPDFFileList()[2].pageCount)
    }

    private fun setupState(): PDFState {
        return PDFState(listOf(
            TestingUtil.createPDFFile(numberOfPages = 3),
            TestingUtil.createPDFFile(numberOfPages = 2),
            TestingUtil.createPDFFile(numberOfPages = 4),
        ))
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
        SystemIOReplacer.restoreSystemOutputs()
    }
}
