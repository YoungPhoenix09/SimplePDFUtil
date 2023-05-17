package org.paynefulapps.simplepdfutil

class PDFState(
    private val pdfFiles: List<PDFFile> = listOf()
) {
    fun getState(): List<PDFFile> = pdfFiles

    fun addFile(pdfFile: PDFFile) =
        PDFState(this.pdfFiles + pdfFile)

    fun removeFile(fileIndex: Int): PDFState {
        if (fileIndex > pdfFiles.size || fileIndex < 1) {
            throw Exception("There is not a file with ID number $fileIndex.")
        }
        return PDFState((pdfFiles - pdfFiles[fileIndex-1]))
    }
}
