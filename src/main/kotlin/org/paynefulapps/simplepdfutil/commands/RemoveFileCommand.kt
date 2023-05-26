package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.commands.base.ArgumentDependentCommand
import kotlin.Exception

class RemoveFileCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : ArgumentDependentCommand(pdfState, commandArguments) {
    override fun execute(): PDFState {
        var newPDFState = pdfState
        commandArguments.forEach { arg ->
            val pdfNumber = arg.toIntOrNull() ?:
                throw Exception(Messages.NOT_INTEGER_ERROR)
            val pdfNumberStateIndex = pdfNumber - 1

            newPDFState = newPDFState.removeFile(pdfState.getPDFFileList()[pdfNumberStateIndex])
        }
        return newPDFState
    }
}
