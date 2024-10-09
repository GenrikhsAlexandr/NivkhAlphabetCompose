package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.CertificateViewModel

@Composable
fun CertificateScreen(
    name: String,
    viewModel: CertificateViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.setName(name)

    LaunchedEffect(Unit) {
        viewModel.saveName(name)
    }

    CertificateLayout(
        name = name,
    )
}

@Composable
fun CertificateLayout(
    name: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorPrimary),
        contentAlignment = Alignment.Center

    ) {
        Image(
            painter = painterResource(id = R.drawable.certificate),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            alignment = Alignment.Center
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(widthDp = 600, heightDp = 700)
@Composable
private fun CertificateScreenPreview() {
    NivkhAlphabetComposeTheme {
        CertificateLayout(
            name = "Александр",
        )
    }
}