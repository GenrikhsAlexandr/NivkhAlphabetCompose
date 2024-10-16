package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels.SettingViewModel

@Composable
fun DialogSettings(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = modifier
                .width(250.dp)
                .wrapContentHeight()
                .clip(ShapeDefaults.Medium)
                .background(colorProgressBar),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 32.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_setting),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .height(60.dp)
                )
                Spacer(modifier = modifier.height(48.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_music),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = modifier
                            .height(60.dp)
                    )
                    Spacer(modifier = modifier.weight(1f))

                    Switch(
                        checked = uiState.isSoundEnable,
                        onCheckedChange = { viewModel.setSoundEnable(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colorPrimary,
                            checkedTrackColor = colorOnPrimary,
                            uncheckedThumbColor = colorPrimary,
                            uncheckedTrackColor = colorText
                        )
                    )
                }
                Spacer(modifier = modifier.height(48.dp))
                TextButton(
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = modifier
                        .width(150.dp)
                        .padding(horizontal = 8.dp)
                        .background(colorPrimary, CircleShape)
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}