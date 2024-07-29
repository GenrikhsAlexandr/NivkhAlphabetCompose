package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model

data class RevisionSecondModel(
    val title: String,
    val wordId: String,
    val icon: String?,
    val isCorrectAnswer: Boolean? = null,
)