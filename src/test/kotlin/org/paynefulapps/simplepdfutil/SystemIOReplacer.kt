package org.paynefulapps.simplepdfutil

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SystemIOReplacer {
    companion object {
        private val originalSystemOut = System.out
        private val originalSystemErr = System.err
        private val originalSystemIn = System.`in`
        val newOut = ByteArrayOutputStream()
        val newErr = ByteArrayOutputStream()
        val newIn = ByteArrayInputStream("".toByteArray())

        fun replaceSystemOutputs() {
            System.setOut(PrintStream(newOut))
            System.setErr(PrintStream(newErr))
        }

        fun restoreSystemOutputs() {
            newOut.reset()
            newErr.reset()
            System.setOut(originalSystemOut)
            System.setErr(originalSystemErr)
        }

        fun replaceSystemInput(newInputStream: ByteArrayInputStream? = newIn) {
            System.setIn(newInputStream)
        }

        fun restoreSystemInput() {
            newIn.reset()
            System.setIn(originalSystemIn)
        }
    }
}
