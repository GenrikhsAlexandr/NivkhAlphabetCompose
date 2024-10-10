package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class CertificateUIState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String? = null,
    val pdfFile: ByteArray? = null
)