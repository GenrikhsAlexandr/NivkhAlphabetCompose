package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.navigator.NavigationDestination

@Composable
fun NotConnected(
    navController: NavController
) {
    val activity = (LocalContext.current as Activity)

    val painter = rememberAsyncImagePainter(model = R.drawable.ic_not_connected)
    Dialog(
        navigationBack = {
            navController.popBackStack(
                NavigationDestination.LettersScreen.destination,
                inclusive = false
            )
        },
        navigationNext = {
            activity.finish()
        },
        painter = painter,
        title = stringResource(id = R.string.notConnected),
        textButtonBack = stringResource(id = R.string.backAlphabet),
        textButtonNext = stringResource(id = R.string.exitApp),
        isVisibleSecondButton = true,
        onDismissRequest = {}
    )
}