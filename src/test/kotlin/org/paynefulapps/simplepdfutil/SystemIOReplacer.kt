package org.paynefulapps.simplepdfutil

import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SystemIOReplacer {
    companion object {
        private val originalSystemOut = System.out
        private val originalSystemErr = System.err
        val newOut = ByteArrayOutputStream()
        val newErr = ByteArrayOutputStream()

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
    }
}
