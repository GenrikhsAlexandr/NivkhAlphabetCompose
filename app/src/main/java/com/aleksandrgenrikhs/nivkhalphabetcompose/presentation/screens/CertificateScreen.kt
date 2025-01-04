package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.AppBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components.DownloadButton
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.CertificateUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.CertificateViewModel

@Composable
fun CertificateScreen(
    name: String,
    viewModel: CertificateViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        bitmap = viewModel.rendersCertificate(name)
    }
    CertificateLayout(
        uiState,
        bitmap,
        onBack = navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificateLayout(
    uiState: CertificateUIState,
    bitmap: Bitmap?,
    onBack: () -> Unit,
) {
    val action: @Composable RowScope.() -> Unit = {
        DownloadButton()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column {
                AppBar.Render(
                    config = AppBar.AppBarConfig.AppBarTask(
                        title = stringResource(id = R.string.certificate),
                        actions = action,
                    ),
                    navigation = onBack
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorPrimary)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.error -> {
                    Text(
                        text = stringResource(id = R.string.error),
                        style = MaterialTheme.typography.titleLarge,
                        color = colorError
                    )
                }

                uiState.loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentSize()
                    )
                }

                else -> {
                    PdfViewer(bitmap)
                }
            }
        }
    }
}

@Composable
fun PdfViewer(bitmap: Bitmap?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewContent(
    @PreviewParameter(CertificateUIStates::class)
    uiState: CertificateUIState,
) {
    NivkhAlphabetComposeTheme {
        CertificateLayout(
            uiState = uiState,
            bitmap = null,
            onBack = { }
        )
    }
}

private class CertificateUIStates :
    PreviewParameterProvider<CertificateUIState> {
    override val values: Sequence<CertificateUIState>
        get() = sequenceOf(
            CertificateUIState(
                error = true,
                loading = false,
            )
        )
}
