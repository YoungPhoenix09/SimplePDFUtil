package org.paynefulapps.simplepdfutil

object Messages {
    val WELCOME = """Welcome to the Simple PDF Utility!
                |
                |We hope you find the functions of this utility useful. For
                |any feedback or support feel free to reach out to Payneful Apps
                |at youngphoenix09@gmail.com.
                |
                |""".trimMargin()
    const val PDF_STATE_NO_FILES = "(No files)"
    const val PDF_STATE_HEADER = "Current file list:"
    const val PROMPT_FOR_COMMAND = "Specify command: "
    const val INVALID_PATH_ERROR = "The arguments you provided do not evaluate to valid file paths."
    const val INVALID_COMMAND_ERROR = "Invalid command specified. Please use one of the following commands: add"
    const val NO_ARGUMENTS_ERROR = "No arguments specified for the command."
}
