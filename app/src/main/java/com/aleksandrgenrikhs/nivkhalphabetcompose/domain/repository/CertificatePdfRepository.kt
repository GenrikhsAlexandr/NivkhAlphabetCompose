package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository

interface CertificatePdfRepository {

    fun downloadCertificatePdf(pdfByteArray: ByteArray, fileName: String): Result<Unit>

    suspend fun generateCertificatePdf(name: String): Result<ByteArray>
}