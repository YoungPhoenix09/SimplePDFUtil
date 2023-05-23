package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.commands.base.Command

class PDFCommandProcessor {
    fun processCommand(currentPDFState: PDFState, commandString: String) =
        parseCommand(currentPDFState, commandString).execute()

    private fun parseCommand(currentPDFState: PDFState, commandString: String): Command {
        val commandStringList = commandString.split(Regex("\\s"), 2)
        val commandType = getCommandTypeFromCommandListString(commandStringList)
        val commandArguments = splitOutCommandArguments(commandStringList)

        return when (commandType) {
            PDFCommandType.ADD_FILE -> AddFileCommand(currentPDFState, commandArguments)
            PDFCommandType.REMOVE_FILE -> RemoveFileCommand(currentPDFState, commandArguments)
            PDFCommandType.EXTRACT -> ExtractCommand(currentPDFState, commandArguments)
            PDFCommandType.EXIT -> ExitCommand(currentPDFState)
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
        if (commandStringList.size > 1) commandStringList[1].split(Regex(",\\s*"))
        else emptyList()

    enum class PDFCommandType(val stringCommand: String) {
        ADD_FILE("add"),
        REMOVE_FILE("remove"),
        EXTRACT("extract"),
        EXIT("exit")
    }
}
