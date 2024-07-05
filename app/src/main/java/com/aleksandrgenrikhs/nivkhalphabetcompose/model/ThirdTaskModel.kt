package com.aleksandrgenrikhs.nivkhalphabetcompose.model

data class ThirdTaskModel(
    val title: String,
    val wordId: String,
    val icon: String?,
    val isCorrectAnswer: Boolean = false,
)