package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.CertificateUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.CertificateViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FILE_NAME
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

@Composable
fun CertificateScreen(
    name: String,
    viewModel: CertificateViewModel = hiltViewModel(),
    pdfByteArray: (ByteArray) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.generateCertificate(name)
        viewModel.saveName(name)
    }
    pdfByteArray(uiState.pdfFile ?: ByteArray(0))

    CertificateLayout(uiState)
}

@Composable
fun CertificateLayout(
    uiState: CertificateUIState
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
            .background(colorPrimary),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current

        fun showToast(message: String) {
            Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
        }

        when {

            uiState.error -> {
                showToast("Файл не сгенерировался")
            }

            uiState.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize()
                )
            }

            else -> {
                println("uiState.pdfFile = ${uiState.pdfFile}")
                uiState.pdfFile?.let { PdfViewerFromFile(it) }
            }
        }
    }
}

@Composable
fun PdfViewerFromFile(pdfFile: ByteArray) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            val tempFile = File(context.cacheDir, FILE_NAME).apply {
                writeBytes(pdfFile)
            }
            PDFView(ctx, null).apply {
                fromFile(tempFile)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .load()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(colorPrimary)
    )
}