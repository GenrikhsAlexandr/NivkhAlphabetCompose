package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model

data class TaskLearnLetterModel(
    val letterId: String,
    val title: String,
    val wordId: String,
    val icon: String?,
    val progress: Int = 0,
    val isCompleted: Boolean = false,
    val isClickable: Boolean = false,
)
