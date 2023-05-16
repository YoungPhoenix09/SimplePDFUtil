package org.paynefulapps.simplepdfutil

class PDFState {
    private val pdfFiles: MutableList<PDFFile> = mutableListOf()

    fun getState(): List<PDFFile> = pdfFiles

    fun addFile(pdfFile: PDFFile) {
        pdfFiles.add(pdfFile)
    }

    fun removeFile(fileIndex: Int) {
        if (fileIndex > pdfFiles.size || fileIndex < 1) {
            throw Exception("There is not a file with ID number $fileIndex.")
        }
        pdfFiles.removeAt(fileIndex-1)
    }
}
