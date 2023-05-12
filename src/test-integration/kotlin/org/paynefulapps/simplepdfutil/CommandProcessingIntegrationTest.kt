package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals

class CommandProcessingIntegrationTest {
    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it can receive a command`() {
        val command = "Add"
        SystemIOReplacer.replaceSystemInput(ByteArrayInputStream(command.toByteArray()))
        SimplePDFUtil()
        val expectedMessage = "${Messages.WELCOME}${Messages.PROMPT_FOR_COMMAND}$command"
        val output = SystemIOReplacer.newOut.toString()
        assertEquals(expectedMessage,output)
    }

    @AfterEach
    fun cleanup() {
        SystemIOReplacer.restoreSystemInput()
        SystemIOReplacer.restoreSystemOutputs()
    }
}
