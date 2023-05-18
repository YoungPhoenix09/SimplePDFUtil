package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.PDFState

abstract class Command(
    protected val pdfState: PDFState
) {
    abstract fun execute(): PDFState
}
