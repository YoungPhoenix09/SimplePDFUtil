package org.paynefulapps.simplepdfutil.commands.base

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

    protected fun checkStateHasFiles() {
        if (pdfState.getPDFFileList().isEmpty())
            throw Exception(Messages.NO_FILES_ERROR)
    }

    protected fun checkFileIdExists(fileId: Int) {
        val fileIdRange = 1..pdfState.getPDFFileList().size
        if (!fileIdRange.contains(fileId))
            throw Exception(Messages.getNoFileIdError(fileId))
    }
}
