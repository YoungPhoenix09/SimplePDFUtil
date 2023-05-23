package org.paynefulapps.simplepdfutil.commands.base

import org.paynefulapps.simplepdfutil.PDFState

abstract class Command(
    protected val pdfState: PDFState
) {
    abstract fun execute(): PDFState
}
