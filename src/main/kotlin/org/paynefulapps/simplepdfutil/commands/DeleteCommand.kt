package org.paynefulapps.simplepdfutil.commands

import org.apache.pdfbox.pdmodel.PDDocument
import org.paynefulapps.simplepdfutil.PDFFile
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.commands.base.PageDependentCommand

class DeleteCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : PageDependentCommand(pdfState, commandArguments) {
    override fun operation(filePageListMapping: Map<PDFFile, List<Int>>): PDFState {
        return deletePages(filePageListMapping).also {
            println()
            println("Delete operation was successful.")
        }
    }

    private fun deletePages(
        filePageListMapping: Map<PDFFile, List<Int>>
    ): PDFState {
        val updateFileList = filePageListMapping.map { (pdfFile, pageList) ->
            PDDocument.load(pdfFile.filePath.toFile()).use { loadedPdf ->
                pageList.map { pageNumber ->
                    loadedPdf.getPage(pageNumber-1)
                }.forEach { page ->
                    loadedPdf.removePage(page)
                }
                loadedPdf.save(pdfFile.filePath.toFile())
                PDFFile(pdfFile.filePath, loadedPdf.numberOfPages)
            }
        }
        return PDFState(updateFileList)
    }
}
