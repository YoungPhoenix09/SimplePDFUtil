package org.paynefulapps.simplepdfutil

import java.io.InputStream
import java.util.Scanner

object UserPrompter {
    private var scanner = Scanner(System.`in`)

    fun promptUser(message: String): String {
        print(message)
        return scanner.nextLine()
    }

    fun replaceInputSource(source: InputStream) {
        scanner = Scanner(source)
    }
}
