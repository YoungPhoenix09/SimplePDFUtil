package org.paynefulapps.simplepdfutil

import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.notExists

class PDFVerifier {
    fun verifyPDFExists(path: Path) {
        if (path.notExists()) throw Exception("No file exists at path ${path.toAbsolutePath()}.")
    }

    fun verifyFileIsPDF(path: Path): Boolean = path.extension == "pdf"
}
