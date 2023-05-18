package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import java.nio.file.InvalidPathException
import kotlin.Exception

class RemoveFileCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : ArgumentDependentCommand(pdfState, commandArguments) {
    override fun execute(): PDFState {
        var newPDFState = pdfState
        return try {
            commandArguments.forEach { arg ->
                val pdfNumber = arg.toIntOrNull() ?:
                    throw Exception(Messages.NOT_INTEGER_ERROR)
                val pdfNumberStateIndex = pdfNumber - 1

                newPDFState = newPDFState.removeFile(pdfState.getState()[pdfNumberStateIndex])
            }
            newPDFState
        } catch (e: InvalidPathException) {
            throw Exception(Messages.INVALID_PATH_ERROR)
        }
    }
}
