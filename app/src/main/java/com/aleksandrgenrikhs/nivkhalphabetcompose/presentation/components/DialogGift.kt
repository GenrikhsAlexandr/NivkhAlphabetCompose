package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aleksandrgenrikhs.nivkhalphabetcompose.R
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.NivkhAlphabetComposeTheme
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorOnPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.CERTIFICATE_SCREEN

@Composable
fun DialogGift(
    isLettersCompleted: Boolean,
    modifier: Modifier = Modifier,
    navController: NavController,
    name: String,
    isNameNotEmpty: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .padding(end = 8.dp)
            .clickable {
                if (isNameNotEmpty) {
                    navController.navigate("$CERTIFICATE_SCREEN/$name")
                }
                expanded = true
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_gift),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .height(50.dp)
        )
    }
    if (expanded) {
        DialogGifItem(
            onDismissRequest = { expanded = false },
            isLettersCompleted = isLettersCompleted,
            navController = navController,
        )
    }
}

@Composable
fun DialogGifItem(
    navController: NavController,
    onDismissRequest: () -> Unit,
    isLettersCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    val targetTextColor = if (isError) {
        MaterialTheme.colorScheme.error
    } else {
        LocalContentColor.current
    }

    val textStyle = MaterialTheme.typography.labelSmall.copy(
        color = targetTextColor,
    )

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .clip(ShapeDefaults.Medium)
                .background(colorProgressBar)
        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 32.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = if (!isLettersCompleted) painterResource(R.drawable.ic_notyet) else painterResource(
                        R.drawable.ic_congratulation
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .height(160.dp)
                )
                Text(
                    text = if (!isLettersCompleted) stringResource(id = R.string.notYet) else stringResource(
                        id = R.string.congratulation
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                if (isLettersCompleted) {
                    TextField(
                        value = name,
                        textStyle = textStyle,
                        placeholder = {
                            if (isError) Text("Поле не должно быть пустым") else
                                Text(text = "Имя Фамилия")
                        },
                        onValueChange = { newName -> name = newName },
                        modifier = modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text,
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = colorOnPrimary,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = colorText,
                            cursorColor = colorCardLetterItem
                        )
                    )
                }
                TextButton(
                    onClick = {
                        if (isLettersCompleted) {
                            if (name.isEmpty()) isError = true else {
                                isError = false
                                navController.navigate("$CERTIFICATE_SCREEN/$name")
                                onDismissRequest()
                            }
                        } else {
                            onDismissRequest()
                        }
                    },
                    modifier = modifier
                        .padding(8.dp)
                        .background(colorPrimary, CircleShape)
                ) {
                    Text(
                        text = if (isLettersCompleted) stringResource(id = R.string.next) else stringResource(
                            id = R.string.ok
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 300, heightDp = 400)
@Composable
private fun DialogGiftPreview() {
    NivkhAlphabetComposeTheme {
        DialogGift(
            isLettersCompleted = true,
            navController = rememberNavController(),
            name = "Иванов Иван Иванович",
            isNameNotEmpty = false
        )
    }
}