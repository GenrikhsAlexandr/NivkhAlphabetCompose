package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.CertificateViewModel

@Composable
fun DownloadButton(
    viewModel: CertificateViewModel = hiltViewModel(),
) {
    var downloadButtonClickable by remember { mutableStateOf(true) }
    val context = LocalContext.current
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    val permission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            handleDownload(viewModel, context)
        } else {
            showToast(context.getString(R.string.permissionDenied))
        }
    }

    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(CircleShape)
            .clickable(
                enabled = downloadButtonClickable,
                onClick = {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            handleDownload(viewModel, context)
                            downloadButtonClickable = false
                        } else {
                            permission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    } else {
                        handleDownload(viewModel, context)
                        downloadButtonClickable = false
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_download),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(40.dp)
        )
    }
}

private fun handleDownload(
    viewModel: CertificateViewModel,
    context: Context
) {
    val downLoadResult = viewModel.downloadCertificate()
    when {
        downLoadResult.isSuccess -> {
            Toast
                .makeText(
                    context,
                    context.getString(R.string.downLoadReceiptSuccess),
                    Toast.LENGTH_SHORT
                )
                .show()
        }

        downLoadResult.isFailure -> {
            val exception = downLoadResult.exceptionOrNull()
            Toast
                .makeText(
                    context,
                    context.getString(
                        R.string.downloadReceiptError,
                        exception?.message ?: "unknown error"
                    ),
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }
}
