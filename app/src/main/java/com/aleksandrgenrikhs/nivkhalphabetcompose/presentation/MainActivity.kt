package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.NivkhAlphabetApp
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NivkhAlphabetComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorPrimary
                ) {
                    NivkhAlphabetApp(navController = navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarAlphabetPreview(
) {
    NivkhAlphabetComposeTheme {
        NivkhAlphabetApp(
            navController = null
        )
    }
}