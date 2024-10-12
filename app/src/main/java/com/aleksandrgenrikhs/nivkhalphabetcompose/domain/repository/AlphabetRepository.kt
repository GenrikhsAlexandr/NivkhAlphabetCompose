package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository

import android.graphics.Bitmap
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import java.io.File

interface AlphabetRepository {

    suspend fun getWords(): Map<String, List<WordModel>>

    suspend fun generateCertificatePdf(name: String): Result<String>

    suspend fun renderPdfPage(pdfFile: File): Bitmap?

    fun downloadCertificatePdf(pdfFilePath: String, fileName: String): Result<Unit>
}