package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorPrimary
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorProgressBar
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorRight
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorText

@Composable
fun NivkhKeyboard(
    input: String,
    isError: Boolean,
    onValueChange: (letter: String) -> Unit,
    onDelete: () -> Unit,
    onDone: (word: String) -> Unit,
    modifier: Modifier = Modifier,
    onClickable: Boolean
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .size(60.dp)
                .padding(horizontal = 32.dp)
                .border(
                    shape = RoundedCornerShape(8.dp),
                    brush = if (!isError) SolidColor(colorProgressBar) else SolidColor(colorError),
                    width = 1.dp
                ),
            contentAlignment = Alignment.Center,
        )
        {
            Text(
                text = input,
                style = MaterialTheme.typography.displayMedium,
                color = if (!isError) colorText else colorError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .wrapContentSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "а",
                onClick = onValueChange,

                )
            LetterButton(
                letter = "б",
                onClick = onValueChange,

                )
            LetterButton(
                letter = "в",
                onClick = onValueChange,

                )
            LetterButton(
                letter = "г",
                onClick = onValueChange,

                )
            LetterButton(
                letter = "ӷ",
                onClick = onValueChange,

                )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "ғ",
                onClick = onValueChange,

                )
            LetterButton(
                letter = "ӻ",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "д",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "е",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ё",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "ж",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "з",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "и",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "й",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "к",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "кʼ",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ӄ",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ӄʼ",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "л",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "м",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "н",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ӈ",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "о",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "п",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "пʼ",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "р",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "р̆",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "с",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "т",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "тʼ",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "у",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ў",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ф",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "х",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ӿ",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "ӽ",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ц",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ч",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ш",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "щ",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LetterButton(
                letter = "ъ",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ы",
                onClick = onValueChange,
            )

            LetterButton(
                letter = "ь",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "э",
                onClick = onValueChange,
            )
            LetterButton(
                letter = "ю",
                onClick = onValueChange,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DeleteButton(onDelete = onDelete)
            LetterButton(
                letter = "я",
                onClick = onValueChange,
            )
            DoneButton(
                onDone = onDone,
                word = input,
                onClickable = onClickable
            )
        }
    }
}

@Composable
private fun LetterButton(
    letter: String,
    onClick: (letter: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp)
            .background(colorPrimary)
            .clip(CircleShape)
            .clickable(onClick = {
                onClick(letter)
            }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = letter,
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Composable
private fun DeleteButton(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorPrimary,
        ),
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp)
            .clip(CircleShape)
            .clickable(onClick = {
                onDelete()
            }),
    ) {
        Text(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            text = "←",
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Composable
private fun DoneButton(
    word: String,
    onDone: (word: String) -> Unit,
    modifier: Modifier = Modifier,
    onClickable: Boolean
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorPrimary,
        ),
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp)
            .clip(CircleShape)
            .clickable(
                onClick = {
                    onDone(word)
                },
                enabled = onClickable
            ),

        ) {
        Text(
            textAlign = TextAlign.Center,
            text = "✓",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.ExtraBold,
            color = colorRight
        )
    }
}