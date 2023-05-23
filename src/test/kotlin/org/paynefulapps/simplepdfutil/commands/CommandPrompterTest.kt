package org.paynefulapps.simplepdfutil.commands

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.paynefulapps.simplepdfutil.UserPrompter
import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.SystemIOReplacer

class CommandPrompterTest {
    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it prompts for a command`() {
        UserPrompter.sendStringsAsInput("Test input")
        UserPrompter.promptUser(Messages.PROMPT_FOR_COMMAND)
        assertEquals(Messages.PROMPT_FOR_COMMAND, SystemIOReplacer.newOut.toString())
    }

    @AfterEach
    fun cleanup() {
        SystemIOReplacer.restoreSystemOutputs()
    }
}
