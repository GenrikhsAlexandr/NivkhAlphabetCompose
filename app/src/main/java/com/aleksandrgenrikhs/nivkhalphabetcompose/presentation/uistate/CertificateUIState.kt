package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate

data class CertificateUIState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val pdfFile: ByteArray? = null
)