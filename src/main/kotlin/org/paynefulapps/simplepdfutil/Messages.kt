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
    const val PROMPT_FOR_NEW_FILE_NAME = "Please specify the file name of the new file: "
    const val INVALID_PATH_ERROR = "The path(s) you provided are invalid."
    const val INVALID_COMMAND_ERROR = "Invalid command specified. Please use one of the following commands: add exit"
    const val NO_ARGUMENTS_ERROR = "No arguments specified for the command."
    const val NOT_INTEGER_ERROR = "The arguments supplied are not integers, which are required for this command."
    const val NO_FILES_ERROR = "There are no files available to perform this operation."

    fun promptForPages(fileId: Int) = "Please list the pages/ranges for file ID $fileId: "

    fun getInvalidPagesError(fileId: Int, pageNumber: Int) =
        "File ID $fileId does not have a page $pageNumber."

    fun getBadPageError(badValue: String) = "Expected a page number or range but found $badValue."

    fun getInvalidRangeError(badValue: String) = "$badValue is an invalid range."

    fun getNoFileIdError(fileId: Int) = "There is not a file with ID number $fileId."
}
