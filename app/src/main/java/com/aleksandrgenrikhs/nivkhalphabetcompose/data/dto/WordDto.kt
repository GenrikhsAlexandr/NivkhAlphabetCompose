package com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WordDto(
    val title: String,
    val wordId: String,
)
