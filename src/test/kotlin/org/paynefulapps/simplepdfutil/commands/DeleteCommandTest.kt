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
        assertEquals(2, newPdfState.getState().size)
        assertEquals(1, newPdfState.getState()[0].pageCount)
        assertEquals(1, newPdfState.getState()[1].pageCount)
    }

    private fun setupState(): PDFState {
        return PDFState(listOf(
            TestingUtil.createPDFFile(numberOfPages = 3),
            TestingUtil.createPDFFile(numberOfPages = 2)
        ))
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
        SystemIOReplacer.restoreSystemOutputs()
    }
}
