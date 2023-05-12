package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimplePDFUtilTest {
    @BeforeEach
    fun setup() {
        SystemIOReplacer.replaceSystemOutputs()
    }

    @Test
    fun `it produces welcome`() {
        val testResponse = "Just a test"
        val expectedMessage = "${Messages.WELCOME}${Messages.PROMPT_FOR_COMMAND}"
        SystemIOReplacer.replaceSystemInput(ByteArrayInputStream(testResponse.toByteArray()))
        SimplePDFUtil()
        assertEquals(expectedMessage, SystemIOReplacer.newOut.toString())
        assertTrue { SystemIOReplacer.newErr.toString().isEmpty() }
    }

    @AfterEach
    fun cleanup() {
        SystemIOReplacer.restoreSystemOutputs()
    }
}
