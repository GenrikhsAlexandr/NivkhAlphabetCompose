package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.CertificateViewModel

@Composable
fun DownloadButton(
    viewModel: CertificateViewModel = hiltViewModel(),
    pdfByteArray: ByteArray
) {
    var downloadButtonClickable by remember { mutableStateOf(true) }
    val context = LocalContext.current

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(CircleShape)
            .clickable(
                enabled = downloadButtonClickable,
                onClick = {
                    val downLoadResult = viewModel.downloadCertificate(pdfByteArray)
                    when {
                        downLoadResult.isSuccess -> {
                            showToast(context.getString(R.string.down_load_receipt_success))
                        }

                        downLoadResult.isFailure -> {
                            val exception = downLoadResult.exceptionOrNull()
                            showToast(
                                context.getString(
                                    R.string.download_receipt_error,
                                    exception?.message ?: "unknown error"
                                )
                            )
                        }
                    }
                    downloadButtonClickable = false
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_download),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(50.dp)
        )
    }
}