package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.commands.base.Command

class HelpCommand(
    pdfState: PDFState
) : Command(pdfState) {
    override fun execute(): PDFState {
        println()
        println(Messages.COMMANDS)
        println()
        return pdfState
    }
}
