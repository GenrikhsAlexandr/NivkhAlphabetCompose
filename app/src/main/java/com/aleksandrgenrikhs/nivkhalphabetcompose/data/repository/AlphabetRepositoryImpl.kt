package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.VisibleForTesting
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.AlphabetDataSource
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.WordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.CONTENT_TYPE_PDF
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
    private val context: Context
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

    override fun downloadCertificatePdf(pdfByteArray: ByteArray, fileName: String): Result<Unit> {
        val contentResolver =
            context.contentResolver
                ?: return Result.failure(IOException("Content resolver is null"))
        if (pdfByteArray.isEmpty()) {
            return Result.failure(IOException("ByteArray is empty"))
        }
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, fileName)
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

            } else {
                val downloadsDirectory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDirectory, fileName)
                FileOutputStream(file).use { outputStream ->
                    outputStream.write(pdfByteArray)
                }
                Result.success(Unit)
            }
        } catch (e: IOException) {
            Result.failure(IOException("Download error ${e.message}"))
        }
    }
}