package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.LettersViewModel

@Composable
fun CertificateScreen(
    navController: NavController,
    name: String,
    viewModel: LettersViewModel = hiltViewModel(),
    onDividerVisibilityChange: (Boolean) -> Unit,
) {

}