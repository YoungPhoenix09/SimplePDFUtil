package org.paynefulapps.simplepdfutil.commands

import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFState

abstract class ArgumentDependentCommand(
    pdfState: PDFState,
    protected val commandArguments: List<String>
) : Command(pdfState) {
    init {
        checkArgumentsExist()
    }

    private fun checkArgumentsExist() {
        if (commandArguments.isEmpty()) {
            throw Exception(Messages.NO_ARGUMENTS_ERROR)
        }
    }
}
