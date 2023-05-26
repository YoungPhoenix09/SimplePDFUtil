package org.paynefulapps.simplepdfutil.commands

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.SystemIOReplacer

class HelpCommandTest {
    private val newLine = System.lineSeparator()

    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it shows command list`() {
        val pdfState = PDFState()
        val command = HelpCommand(pdfState)
        command.execute()
        assertEquals("$newLine${Messages.COMMANDS}$newLine$newLine", SystemIOReplacer.newOut.toString())
    }

    @AfterEach
    fun cleanup() {
        SystemIOReplacer.restoreSystemOutputs()
    }
}
