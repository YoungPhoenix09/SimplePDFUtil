package org.paynefulapps.simplepdfutil.commands

import org.apache.pdfbox.pdmodel.PDDocument
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.paynefulapps.simplepdfutil.*
import kotlin.test.assertTrue

class ReorderCommandTest {
    private val pdfState = setupState()

    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it can reorder file pages`() {
        UserPrompter.sendStringsAsInput("2,1,3")
        val command = ReorderCommand(pdfState, listOf("1"))
        val newPdfState = command.execute()
        val pageContents = PDDocument.load(newPdfState.getState()[0].filePath.toFile()).use { modifiedPdf ->
            val pdfPage = modifiedPdf.getPage(0)
            pdfPage.contents.bufferedReader().use { it.readText() }
        }
        assertTrue { pageContents.contains("Page 2") }
    }

    @Test
    fun `it can add remaining pages to list when reordering`() {
        UserPrompter.sendStringsAsInput("2,1")
        val command = ReorderCommand(pdfState, listOf("1"))
        val newPdfState = command.execute()
        val pageContents = PDDocument.load(newPdfState.getState()[0].filePath.toFile()).use { modifiedPdf ->
            val pdfPage = modifiedPdf.getPage(2)
            pdfPage.contents.bufferedReader().use { it.readText() }
        }
        assertTrue { pageContents.contains("Page 3") }
    }

    private fun setupState(): PDFState {
        return PDFState(listOf(
            TestingUtil.createPDFFile(numberOfPages = 3)
        ))
    }

    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
        SystemIOReplacer.restoreSystemOutputs()
    }
}
