package org.paynefulapps.simplepdfutil

class PDFState(
    private val pdfFiles: List<PDFFile> = listOf()
) {
    fun getPDFFileList(): List<PDFFile> = pdfFiles

    fun printState() {
        println(Messages.PDF_STATE_HEADER)
        if (pdfFiles.isEmpty()) println(Messages.PDF_STATE_NO_FILES)
        pdfFiles.forEachIndexed { index, pdfFile ->
            println("${index+1}. ${pdfFile.filePath.fileName} - ${pdfFile.pageCount} page(s)")
        }
        println()
    }

    fun addFile(pdfFile: PDFFile) =
        PDFState(this.pdfFiles + pdfFile)

    fun removeFile(fileIndex: Int): PDFState {
        if (fileIndex > pdfFiles.size || fileIndex < 1) {
            throw Exception(Messages.getNoFileIdError(fileIndex))
        }
        return PDFState((pdfFiles - pdfFiles[fileIndex-1]))
    }

    fun removeFile(pdfFile: PDFFile): PDFState {
        return try {
            PDFState((pdfFiles - pdfFile))
        } catch (e: Exception) {
            throw Exception("The file does not exist in the state.")
        }
    }
}
