package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model

data class RevisionListenAndChooseModel(
    val letter: String,
    val isCorrectAnswer: Boolean? = null,
)
