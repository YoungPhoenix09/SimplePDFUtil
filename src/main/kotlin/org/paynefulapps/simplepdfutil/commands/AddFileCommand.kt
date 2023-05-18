package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.PDFVerifier
import java.nio.file.InvalidPathException
import java.nio.file.Path
import kotlin.Exception

class AddFileCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : Command(pdfState, commandArguments) {
    override fun execute(): PDFState {
        var newPDFState = pdfState
        return try {
            commandArguments.forEach { arg ->
                val path = Path.of(arg)
                val pdfFile = PDFVerifier.verifyFile(path)
                newPDFState = newPDFState.addFile(pdfFile)
            }
            newPDFState
        } catch (e: InvalidPathException) {
            throw Exception(Messages.INVALID_PATH_ERROR)
        }
    }
}
