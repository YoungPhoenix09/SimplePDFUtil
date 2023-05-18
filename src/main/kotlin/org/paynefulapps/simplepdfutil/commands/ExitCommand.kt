package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.PDFState

class ExitCommand(
    pdfState: PDFState
) : Command(pdfState) {
    override fun execute() = pdfState
}
