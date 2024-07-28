package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model

data class SecondTaskModel(
    val letterId: String,
    val title: String,
    val wordId: String,
    val icon: String?,
    val isFlipped: Boolean = false,
    val isCorrectAnswer: Boolean = false,
)