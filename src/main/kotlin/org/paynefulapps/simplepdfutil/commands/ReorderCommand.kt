package org.paynefulapps.simplepdfutil.commands

import org.apache.pdfbox.pdmodel.PDDocument
import org.paynefulapps.simplepdfutil.PDFFile
import org.paynefulapps.simplepdfutil.PDFState
import org.paynefulapps.simplepdfutil.commands.base.PageDependentCommand

class ReorderCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : PageDependentCommand(pdfState, commandArguments) {
    override fun operation(filePageListMapping: Map<PDFFile, List<Int>>): PDFState {
        return reorderPages(filePageListMapping).also {
            println()
            println("Pages were successfully reordered.")
        }
    }

    private fun reorderPages(
        filePageListMapping: Map<PDFFile, List<Int>>
    ): PDFState {
        filePageListMapping.forEach { (pdfFile, pageList) ->
            val fullPageList = getFullPageList(pageList, pdfFile.pageCount)
            val pdfIOFile = pdfFile.filePath.toFile()
            PDDocument.load(pdfIOFile).use { loadedPdf ->
                PDDocument().use { reorderedDoc ->
                    fullPageList.forEach { pageNumber ->
                        val pdfPage = loadedPdf.getPage(pageNumber-1)
                        reorderedDoc.addPage(pdfPage)
                    }
                    reorderedDoc.save(pdfIOFile)
                }
            }
        }
        return pdfState
    }

    private fun getFullPageList(
        pageList: List<Int>,
        expectedPageCount: Int
    ): List<Int> {
        return if (pageList.size != expectedPageCount) {
            val fullPageRange = 1..expectedPageCount
            val remainingPages = fullPageRange.toList() - pageList.toSet()
            pageList + remainingPages
        } else pageList
    }
}
