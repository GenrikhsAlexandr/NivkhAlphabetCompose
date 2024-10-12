package com.aleksandrgenrikhs.nivkhalphabetcompose.data

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FILE_NAME
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class AlphabetDataSource
@Inject constructor(
    private val context: Context,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {

    suspend fun getWords(): List<SubjectDto> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(WORDS_URL)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonString = reader.use { it.readText() }
                val response = json.decodeFromString<List<SubjectDto>>(jsonString)
                response
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun generateCertificatePdf(name: String): Result<String> {
        val pdfFile = File(context.cacheDir, FILE_NAME)

        if (pdfFile.exists()) {
            return Result.success(pdfFile.absolutePath)
        }
        return withContext(Dispatchers.IO) {
            try {
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

                FileOutputStream(pdfFile).use { outputStream ->
                    document.writeTo(outputStream)
                }
                document.close()

                Result.success(pdfFile.absolutePath)
            } catch (e: IOException) {
                Result.failure(IOException("Error generating PDF ${e.message}"))
            }
        }
    }

    fun renderPdfPage(pdfFile: File): Bitmap? {
        return try {
            val fileDescriptor =
                ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(fileDescriptor)
            val page = pdfRenderer.openPage(0)
            val width = page.width
            val height = page.height
            val tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            page.render(tempBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            pdfRenderer.close()
            fileDescriptor.close()
            tempBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun getContentResolver(): ContentResolver {
        return context.contentResolver ?: throw IOException("Content resolver is null")
    }
}