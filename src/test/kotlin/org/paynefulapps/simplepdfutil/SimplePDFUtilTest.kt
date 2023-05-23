package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimplePDFUtilTest {
    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it produces welcome`() {
        val newLine = System.lineSeparator()
        val expectedMessage = "${Messages.WELCOME}${Messages.PDF_STATE_HEADER}$newLine${Messages.PDF_STATE_NO_FILES}$newLine$newLine${Messages.PROMPT_FOR_COMMAND}$newLine$newLine"
        UserPrompter.sendStringsAsInput("exit")
        SimplePDFUtil()
        assertEquals(expectedMessage, SystemIOReplacer.newOut.toString())
        assertTrue { SystemIOReplacer.newErr.toString().isEmpty() }
    }

    @AfterEach
    fun cleanup() {
        SystemIOReplacer.restoreSystemOutputs()
    }
}
