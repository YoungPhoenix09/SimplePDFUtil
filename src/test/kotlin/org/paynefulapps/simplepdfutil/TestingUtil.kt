package org.paynefulapps.simplepdfutil

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.nio.file.Files
import java.util.concurrent.ConcurrentHashMap
import kotlin.io.path.deleteIfExists
import kotlin.io.path.name

object TestingUtil {
    private val createdFiles = ConcurrentHashMap<String,PDFFile>()

    fun createPDFFile(name: String? = null, numberOfPages: Int = 1): PDFFile {
        val filePath = Files.createTempFile(name, ".pdf")
        val pdf = PDDocument().also { doc ->
            repeat(numberOfPages) { index ->
                val font = PDType1Font.HELVETICA_BOLD
                val page = PDPage()
                doc.addPage(page)
                PDPageContentStream(doc, page).use { contents ->
                    contents.beginText()
                    contents.setFont(font, 12F)
                    contents.showText("Page ${index+1}")
                    contents.endText()
                }
            }
            doc.save(filePath.toFile())
        }
        return pdf.use {
            PDFFile(
                filePath,
                it.numberOfPages
            ).also { pdfFile -> createdFiles[pdfFile.filePath.name] = pdfFile }
        }
    }

    fun setupPDFStateWithFiles(): PDFState {
        val pdfFile1 = createPDFFile()
        val pdfFile2 = createPDFFile()
        val pdfFile3 = createPDFFile()
        return PDFState(listOf(
            pdfFile1,
            pdfFile2,
            pdfFile3,
        ))
    }

    fun cleanupCreatedFiles() = createdFiles.forEach {
        it.value.filePath.deleteIfExists()
        createdFiles.remove(it.key)
    }
}
