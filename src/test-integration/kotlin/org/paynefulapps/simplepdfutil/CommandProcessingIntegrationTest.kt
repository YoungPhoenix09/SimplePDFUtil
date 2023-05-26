package org.paynefulapps.simplepdfutil

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively

class CommandProcessingIntegrationTest {
    private val tempDirPath = Files.createTempDirectory(null)

    @Test
    fun `it can do all operations without error`() {
        val pathToNewFile = Path.of(tempDirPath.toString(), "newFile.pdf")
        val pathToMergedFile = Path.of(tempDirPath.toString(), "mergedFile.pdf")
        val testFile1 = TestingUtil.createPDFFile()
        val testFile2 = TestingUtil.createPDFFile()
        UserPrompter.sendStringsAsInput(
            "add ${testFile1.filePath}, ${testFile2.filePath}",
            "extract 1, 2",
            "1",
            "1",
            "$pathToNewFile",
            "merge 1,2,3",
            "$pathToMergedFile",
            "reorder 4",
            "2-3,1",
            "delete 1",
            "1",
            "remove 4",
            "help",
            "exit"
        )
        assertDoesNotThrow { SimplePDFUtil() }
    }

    @OptIn(ExperimentalPathApi::class)
    @AfterEach
    fun cleanup() {
        TestingUtil.cleanupCreatedFiles()
        tempDirPath.deleteRecursively()
    }
}
