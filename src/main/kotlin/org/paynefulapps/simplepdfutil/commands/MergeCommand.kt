package org.paynefulapps.simplepdfutil.commands

import org.apache.pdfbox.multipdf.PDFMergerUtility
import org.apache.pdfbox.pdmodel.PDDocument
import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFFile
import org.paynefulapps.simplepdfutil.UserPrompter
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.commands.base.ArgumentDependentCommand
import java.nio.file.Path

class MergeCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : ArgumentDependentCommand(pdfState, commandArguments) {
    private val pdfMerger = PDFMergerUtility()

    override fun execute(): PDFState {
        checkStateHasFiles()
        val fileNameString = UserPrompter.promptUser(Messages.PROMPT_FOR_NEW_FILE_NAME)
        val fileName = try {
            Path.of(fileNameString)
        } catch (e: Exception) {
            throw Exception(Messages.INVALID_PATH_ERROR)
        }
        val newPdfFile = mergePagesIntoNewFile(fileName)
        println()
        println("Pages were merged into new file ${newPdfFile.filePath}.")
        val updatedStateFileList = pdfState.getState() + listOf(newPdfFile)
        return PDFState(updatedStateFileList)
    }

    private fun mergePagesIntoNewFile(
        fileName: Path
    ) = PDDocument().use { newDoc ->
        commandArguments.forEach { argument ->
            val fileId = argument.toIntOrNull() ?:
                throw Exception(Messages.NOT_INTEGER_ERROR)
            checkFileIdExists(fileId)
            val pdfFile = pdfState.getState()[fileId-1]
            PDDocument.load(pdfFile.filePath.toFile()).use { loadedPdf ->
                pdfMerger.appendDocument(newDoc, loadedPdf)
            }
        }
        newDoc.save(fileName.toFile())
        PDFFile(fileName, newDoc.numberOfPages)
    }
}
