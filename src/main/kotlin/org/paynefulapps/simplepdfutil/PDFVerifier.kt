package org.paynefulapps.simplepdfutil

import org.apache.pdfbox.pdmodel.PDDocument
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.notExists

class PDFVerifier {
    fun verifyFile(path: Path): PDFFile {
        verifyPDFExists(path)
        verifyFileIsPDF(path)

        return PDDocument.load(path.toFile()).use {
            PDFFile(
                path,
                it.numberOfPages
            )
        }
    }

    private fun verifyPDFExists(path: Path) {
        if (path.notExists()) throw Exception("No file exists at path ${path.toAbsolutePath()}.")
    }

    private fun verifyFileIsPDF(path: Path) {
        if (path.extension != "pdf") throw Exception("${path.fileName} is not a PDF.")
    }
}
