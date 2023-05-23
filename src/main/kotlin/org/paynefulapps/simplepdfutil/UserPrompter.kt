package org.paynefulapps.simplepdfutil

import java.io.ByteArrayInputStream
import java.util.Scanner

object UserPrompter {
    private var scanner = Scanner(System.`in`)

    fun promptUser(message: String): String {
        print(message)
        return scanner.nextLine()
    }

    /**
     * This function is just for test purposes and allows a series of strings
     * to be sent as lines of input. Useful for test scenarios where input is needed
     * to satisfy expected user prompt responses.
     */
    fun sendStringsAsInput(vararg inputString: String) {
        var joinedInputString = ""
        inputString.forEach { if (joinedInputString.isEmpty()) {
            joinedInputString = it + System.lineSeparator()
        } else joinedInputString += it + System.lineSeparator() }
        val commandInputStream = ByteArrayInputStream(joinedInputString.toByteArray())
        scanner = Scanner(commandInputStream)
    }
}
