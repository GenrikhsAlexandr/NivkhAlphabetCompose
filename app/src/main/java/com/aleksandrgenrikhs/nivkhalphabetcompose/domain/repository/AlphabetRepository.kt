package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository

import android.graphics.Bitmap
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel

interface AlphabetRepository {

    suspend fun getWords(): Map<String, List<WordModel>>

    suspend fun getPdfPage(name: String): Bitmap?

    fun downloadCertificatePdf(): Result<Unit>
}
