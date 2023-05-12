package org.paynefulapps.simplepdfutil

import java.util.Scanner

class CommandPrompter {
    private val scanner = Scanner(System.`in`)

    fun promptForCommand(): String {
        print(Messages.PROMPT_FOR_COMMAND)
        return scanner.nextLine()
    }
}
