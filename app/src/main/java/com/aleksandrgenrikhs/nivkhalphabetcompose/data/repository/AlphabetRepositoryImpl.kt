package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.AlphabetDataSource
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.WordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.CONTENT_TYPE_PDF
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FILE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class AlphabetRepositoryImpl
@Inject constructor(
    private val wordMapper: WordMapper,
    private val dataSource: AlphabetDataSource,
) : AlphabetRepository {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var words: Map<String, List<WordModel>>? = null

    override suspend fun getWords(): Map<String, List<WordModel>> {
        return words ?: withContext(Dispatchers.IO) {
            try {
                words = wordMapper.map(dataSource.getWords())
                words!!
            } catch (e: Exception) {
                emptyMap()
            }
        }
    }

    override suspend fun getPdfPage(name: String): Bitmap? {
        return dataSource.renderPdfPage(name)
    }

    override fun downloadCertificatePdf(): Result<Unit> {
        val pdfFilePath = dataSource.getPdfFilePath().absolutePath
        val contentResolver = dataSource.getContentResolver()

        val pdfByteArray = readPdfBytes(pdfFilePath)
            ?: return Result.failure(IOException("Error reading PDF file"))

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            savePdfToMediaStore(contentResolver, pdfByteArray)
        } else {
            savePdfToDownloadsDirectory(pdfByteArray)
        }
    }

    private fun readPdfBytes(pdfFilePath: String): ByteArray? {
        return try {
            File(pdfFilePath).readBytes()
        } catch (e: IOException) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun savePdfToMediaStore(
        contentResolver: ContentResolver,
        pdfByteArray: ByteArray,
    ): Result<Unit> {
        return try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, FILE_NAME)
                put(MediaStore.Downloads.MIME_TYPE, CONTENT_TYPE_PDF)
            }
            val uri =
                contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                contentResolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(pdfByteArray)
                }
                Result.success(Unit)
            } ?: Result.failure(IOException("Download error"))
        } catch (e: IOException) {
            Result.failure(IOException("Download error ${e.message}"))
        }
    }

    private fun savePdfToDownloadsDirectory(pdfByteArray: ByteArray): Result<Unit> {
        return try {
            val downloadsDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDirectory, FILE_NAME)
            FileOutputStream(file).use { outputStream ->
                outputStream.write(pdfByteArray)
            }
            Result.success(Unit)
        } catch (e: IOException) {
            Result.failure(IOException("Download error ${e.message}"))
        }
    }
}
