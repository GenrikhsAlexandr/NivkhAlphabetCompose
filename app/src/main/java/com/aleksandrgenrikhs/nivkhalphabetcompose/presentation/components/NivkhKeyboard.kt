package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorCardLetterItem
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.ui.theme.colorError
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
    modifier: Modifier,
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
                .size(50.dp)
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
        Spacer(modifier = modifier.height(8.dp))
        Box(
            modifier = modifier
                .background(colorProgressBar)
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        )
        {
            Column(
                modifier = modifier
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LetterButton(
                        letter = "а",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "б",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "в",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "г",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ӷ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ғ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LetterButton(
                        letter = "ӻ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "д",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "е",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ё",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ж",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "з",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LetterButton(
                        letter = "и",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "й",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "к",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "кʼ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ӄ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ӄʼ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LetterButton(
                        letter = "л",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "м",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "н",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ӈ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "о",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "п",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LetterButton(
                        letter = "пʼ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "р",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "р̆",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "с",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "т",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "тʼ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LetterButton(
                        letter = "у",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ў",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ф",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "х",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ӿ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ӽ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LetterButton(
                        letter = "ц",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ч",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ш",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "щ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ъ",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ы",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DeleteButton(onDelete = onDelete, modifier = Modifier.weight(1f))
                    LetterButton(
                        letter = "ь",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "э",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "ю",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    LetterButton(
                        letter = "я",
                        onClick = onValueChange,
                        modifier = Modifier.weight(1f)
                    )
                    DoneButton(
                        onDone = onDone,
                        modifier = Modifier.weight(1f),
                        word = input,
                        onClickable = onClickable
                    )
                }
            }
        }
    }
}

@Composable
private fun LetterButton(
    letter: String,
    onClick: (letter: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = {
            onClick(letter)
        },
        modifier = modifier
            .wrapContentSize(),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(colorCardLetterItem),
        )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = letter,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun DeleteButton(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = {
            onDelete()
        },
        modifier = modifier
            .wrapContentSize(),

        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(colorCardLetterItem)
        )
    ) {
        Text(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            text = "←",
            style = MaterialTheme.typography.bodyLarge,
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
    OutlinedButton(
        onClick = {
            onDone(word)
        },
        enabled = onClickable,
        modifier = modifier
            .wrapContentSize(),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(colorCardLetterItem)
        )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "✓",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            color = colorRight
        )
    }
}