package org.paynefulapps.simplepdfutil

import org.paynefulapps.simplepdfutil.commands.PDFCommandProcessor

class SimplePDFUtil {
    private val commandProcessor = PDFCommandProcessor()
    private var pdfState = PDFState()

    init {
        displayWelcome()
        do {
            pdfState.printState()
            val commandString = UserPrompter.promptUser(Messages.PROMPT_FOR_COMMAND)
            println()
            println()
            try {
                pdfState = commandProcessor.processCommand(pdfState, commandString)
            } catch (e: Exception) {
                println(e.message)
            }
        } while (commandString.lowercase() != "exit")
    }

    private fun displayWelcome() {
        print(Messages.WELCOME)
    }
}

fun main() {
    SimplePDFUtil()
}
