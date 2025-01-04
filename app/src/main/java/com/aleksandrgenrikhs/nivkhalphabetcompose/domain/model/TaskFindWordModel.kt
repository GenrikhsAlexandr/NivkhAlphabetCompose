package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model

data class TaskFindWordModel(
    val letterId: String,
    val title: String,
    val wordId: String,
    val icon: String?,
    val isFlipped: Boolean = false,
    val isCorrectAnswer: Boolean = false,
)
