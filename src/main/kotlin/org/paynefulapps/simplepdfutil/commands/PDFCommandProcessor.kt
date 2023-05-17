package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState

class PDFCommandProcessor {
    fun processCommand(currentPDFState: PDFState, commandString: String) =
        parseCommand(currentPDFState, commandString).execute()

    private fun parseCommand(currentPDFState: PDFState, commandString: String): Command {
        val commandStringList = commandString.split(Regex("\\s"))
        val commandType = getCommandTypeFromCommandListString(commandStringList)
        val commandArguments = if (commandStringList.size < 2) {
            throw Exception(Messages.NO_ARGUMENTS_ERROR)
        } else splitOutCommandArguments(commandStringList)

        return when (commandType) {
            PDFCommandType.ADD_FILE -> AddFileCommand(currentPDFState, commandArguments)
        }
    }

    private fun getCommandTypeFromCommandListString(
        commandStringList: List<String>
    ): PDFCommandType {
        val validCommandMap = PDFCommandType.values().associateBy { it.stringCommand }
        val specifiedCommand = commandStringList[0].lowercase()
        return validCommandMap[specifiedCommand] ?:
            throw Exception(Messages.INVALID_COMMAND_ERROR)
    }

    private fun splitOutCommandArguments(commandStringList: List<String>) =
        commandStringList.slice(1 until commandStringList.size).toTypedArray()

    enum class PDFCommandType(val stringCommand: String) {
        ADD_FILE("add")
    }
}
