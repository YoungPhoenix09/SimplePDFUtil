package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.PDFVerifier
import java.nio.file.Path

class AddFileCommand(
    pdfState: PDFState,
    commandArguments: Array<String>
) : Command(pdfState, commandArguments) {
    override fun execute(): PDFState {
        commandArguments.forEach { arg ->
            val path = Path.of(arg)
            val pdfVerifier = PDFVerifier()
            val pdfFile = pdfVerifier.verifyFile(path)
            pdfState.addFile(pdfFile)
        }
        return pdfState
    }
}