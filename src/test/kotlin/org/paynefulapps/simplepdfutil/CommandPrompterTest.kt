package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class CommandPrompterTest {
    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it prompts for a command`() {
        val testResponse = "Test input"
        SystemIOReplacer.replaceSystemInput(ByteArrayInputStream(testResponse.toByteArray()))
        CommandPrompter().promptForCommand()
        assertEquals(Messages.PROMPT_FOR_COMMAND, SystemIOReplacer.newOut.toString())
    }

    @AfterEach
    fun cleanup() {
        SystemIOReplacer.restoreSystemInput()
        SystemIOReplacer.restoreSystemOutputs()
    }
}
