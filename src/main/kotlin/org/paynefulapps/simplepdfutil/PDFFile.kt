package org.paynefulapps.simplepdfutil

import java.nio.file.Path

data class PDFFile(
    val filePath: Path,
    val pageCount: Int
)
