package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@Composable
fun AboutMenu(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .wrapContentSize()
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Меню",
            tint = colorText
        )

        DropdownMenu(
            modifier = modifier
                .background(colorProgressBar)
                .align(Alignment.Center)
                .wrapContentSize(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = onClick,
                text = {
                    Text(
                        text = stringResource(id = R.string.about),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colorText
                    )
                }
            )
        }
    }
}