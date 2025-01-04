package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model

data class RevisionChooseRightWordModel(
    val title: String,
    val wordId: String,
    val icon: String?,
    val isCorrectAnswer: Boolean? = null,
)
