package org.paynefulapps.simplepdfutil.commands

import org.apache.pdfbox.pdmodel.PDDocument
import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFFile
import org.paynefulapps.simplepdfutil.UserPrompter
import org.paynefulapps.simplepdfutil.PDFState
import java.nio.file.Path

class MergeCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : PageDependentCommand(pdfState, commandArguments) {
    override fun operation(filePageListMapping: Map<PDFFile, List<Int>>): PDFState {
        val fileNameString = UserPrompter.promptUser(Messages.PROMPT_FOR_NEW_FILE_NAME)
        val fileName = try {
            Path.of(fileNameString)
        } catch (e: Exception) {
            throw Exception(Messages.INVALID_PATH_ERROR)
        }
        val newPdfFile = mergePagesIntoNewFile(fileName, filePageListMapping)
        println()
        println("Pages were merged into new file ${newPdfFile.filePath}.")
        val updatedStateFileList = pdfState.getState() + listOf(newPdfFile)
        return PDFState(updatedStateFileList)
    }

    private fun mergePagesIntoNewFile(
        fileName: Path,
        filePageListMapping: Map<PDFFile, List<Int>>
    ) = PDDocument().use { newDoc ->
        filePageListMapping.forEach { (pdfFile, pageList) ->
            PDDocument.load(pdfFile.filePath.toFile()).use { loadedPdf ->
                pageList.forEach { pageNumber ->
                    val pdfPage = loadedPdf.getPage(pageNumber-1)
                    newDoc.addPage(pdfPage)
                }
            }
        }
        newDoc.save(fileName.toFile())
        PDFFile(fileName, newDoc.numberOfPages)
    }
}
