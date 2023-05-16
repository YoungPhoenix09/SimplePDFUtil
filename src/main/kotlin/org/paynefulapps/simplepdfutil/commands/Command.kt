package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.PDFState

abstract class Command(
    protected val pdfState: PDFState,
    protected val commandArguments: Array<String>
) {
    abstract fun execute(): PDFState
}
