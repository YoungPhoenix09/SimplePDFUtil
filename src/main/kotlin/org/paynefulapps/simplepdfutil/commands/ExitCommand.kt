package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.commands.base.Command

class ExitCommand(
    pdfState: PDFState
) : Command(pdfState) {
    override fun execute() = pdfState
}
