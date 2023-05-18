package org.paynefulapps.simplepdfutil

import org.paynefulapps.simplepdfutil.commands.PDFCommandProcessor

class SimplePDFUtil {
    private val commandPrompter = CommandPrompter()
    private val commandProcessor = PDFCommandProcessor()
    private var pdfState = PDFState()

    init {
        displayWelcome()
        do {
            pdfState.printState()
            val commandString = commandPrompter.promptForCommand()
            println()
            println()
            pdfState = commandProcessor.processCommand(pdfState, commandString)
        } while (commandString.lowercase() != "exit")
    }

    private fun displayWelcome() {
        print(Messages.WELCOME)
    }
}

fun main(vararg args: String) {
    SimplePDFUtil()
}
