package org.paynefulapps.simplepdfutil
class SimplePDFUtil {
    private val commandPrompter = CommandPrompter()
    private val pdfState = PDFState()

    init {
        displayWelcome()
        do {
            pdfState.printState()
            val command = commandPrompter.promptForCommand()
            println()
            println()
        } while (command != "exit")
    }

    private fun displayWelcome() {
        print(Messages.WELCOME)
    }
}

fun main(vararg args: String) {
    SimplePDFUtil()
}
