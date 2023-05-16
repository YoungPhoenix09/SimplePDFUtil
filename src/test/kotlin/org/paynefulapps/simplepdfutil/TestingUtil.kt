package org.paynefulapps.simplepdfutil

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import java.nio.file.Files
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.deleteIfExists
import kotlin.io.path.name

object TestingUtil {
    private val createdFiles = ConcurrentHashMap<String,PDFFile>()

    fun createPDFFile(name: String? = null): PDFFile {
        val filePath = Files.createTempFile(name, ".pdf")
        val pdf = PDDocument().also {
            it.addPage(PDPage())
            it.save(filePath.toFile())
        }
        return pdf.use {
            PDFFile(
                filePath,
                it.numberOfPages
            ).also { pdfFile -> createdFiles[pdfFile.filePath.name] = pdfFile }
        }
    }

    fun cleanupCreatedFiles() = createdFiles.forEach {
        it.value.filePath.deleteIfExists()
        createdFiles.remove(it.key)
    }
}
