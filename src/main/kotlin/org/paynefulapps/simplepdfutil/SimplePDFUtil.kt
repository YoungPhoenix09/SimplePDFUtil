package org.paynefulapps.simplepdfutil
class SimplePDFUtil {
    private val commandPrompter = CommandPrompter()

    init {
        displayWelcome()
        val command = commandPrompter.promptForCommand()
        print(command)
    }

    private fun displayWelcome() {
        print(Messages.WELCOME)
    }
}

fun main(vararg args: String) {
    SimplePDFUtil()
}
