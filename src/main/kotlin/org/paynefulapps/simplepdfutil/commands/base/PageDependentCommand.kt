package org.paynefulapps.simplepdfutil.commands.base

import org.paynefulapps.simplepdfutil.UserPrompter
import org.paynefulapps.simplepdfutil.Messages
import org.paynefulapps.simplepdfutil.PDFFile
import org.paynefulapps.simplepdfutil.PDFState

abstract class PageDependentCommand(
    pdfState: PDFState,
    commandArguments: List<String>
) : ArgumentDependentCommand(pdfState, commandArguments) {
    init {
        checkStateHasFiles()
    }

    protected abstract fun operation(filePageListMapping: Map<PDFFile, List<Int>>): PDFState

    override fun execute(): PDFState {
        val pdfFileList = pdfState.getState()
        val filePageListMapping = commandArguments.associate { arg ->
            val pdfFileId = arg.toIntOrNull() ?:
                throw Exception(Messages.NOT_INTEGER_ERROR)
            checkFileIdExists(pdfFileId)
            val responseForPages = UserPrompter.promptUser(Messages.promptForPages(pdfFileId))
            println()
            val pageNumbers = parsePages(responseForPages)
            pageNumbers.forEach { checkPageNumberIsInPdfPageRange(pdfFileId, it) }
            pdfFileList[pdfFileId-1] to pageNumbers
        }
        return operation(filePageListMapping)
    }

    fun parsePages(pages: String): List<Int> {
        val listOfPages = pages.split(Regex(",\\s*"))
        return listOfPages.map { pageSpecification ->
            val trimmedPageSpecification = pageSpecification.trim()
            val dashSeparator = Regex("\\s*-\\s*")
            if (trimmedPageSpecification.contains(dashSeparator)) {
                convertPageRangeStringToPageNumbers(trimmedPageSpecification)
            } else {
                val pageNumber = trimmedPageSpecification.toIntOrNull() ?:
                    throw Exception(Messages.getBadPageError(trimmedPageSpecification))
                listOf(pageNumber)
            }
        }.flatten()
    }

    private fun convertPageRangeStringToPageNumbers(stringValue: String): List<Int> {
        val dashSeparator = Regex("\\s*-\\s*")
        val (upperBoundString, lowerBoundString) = stringValue.split(dashSeparator, 2)
        val upperBound = upperBoundString.toIntOrNull()
        val lowerBound = lowerBoundString.toIntOrNull()

        if (upperBound == null || lowerBound == null)
            throw Exception(Messages.getInvalidRangeError(stringValue))
        return (upperBound..lowerBound).toList()
    }

    private fun checkPageNumberIsInPdfPageRange(fileId: Int, pageNumber: Int) {
        val pdfFile = pdfState.getState()[fileId-1]
        val pdfPageRange = 1..pdfFile.pageCount
        if (!pdfPageRange.contains(pageNumber))
            throw Exception(Messages.getInvalidPagesError(fileId, pageNumber))
    }
}
