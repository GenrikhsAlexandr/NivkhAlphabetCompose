package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.CertificatePdfRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.CONTENT_TYPE_PDF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class CertificatePdfRepositoryImpl
@Inject constructor(
    private val context: Context,
) : CertificatePdfRepository {

    override suspend fun generateCertificatePdf(name: String): Result<ByteArray> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val pageWidth = 2480
                val pageHeight = 3508
                val bmp = BitmapFactory.decodeResource(context.resources, R.drawable.certificate)
                val document = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
                val page = document.startPage(pageInfo)
                val canvas = page.canvas
                val srcRect = Rect(0, 0, bmp.width, bmp.height)
                val destRect = Rect(0, 0, pageWidth, pageHeight)
                canvas.drawBitmap(bmp, srcRect, destRect, null)
                val paint = Paint().apply {
                    color = Color.BLACK
                    textSize = 200f
                    textAlign = Paint.Align.CENTER
                }
                val x = pageWidth / 2f
                val y = pageHeight / 2f
                canvas.drawText(name, x, y, paint)
                document.finishPage(page)
                val outputStream = ByteArrayOutputStream()
                document.writeTo(outputStream)
                document.close()
                Result.success(outputStream.toByteArray())
            } catch (e: IOException) {
                Result.failure(IOException("Error generating PDF ${e.message}"))
            }
        }

    override fun downloadCertificatePdf(
        pdfByteArray: ByteArray,
        fileName: String
    ): Result<Unit> {
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
                    contentResolver.insert(
                        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
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