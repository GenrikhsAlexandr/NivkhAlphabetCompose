package com.aleksandrgenrikhs.nivkhalphabetcompose.data.firsttask.dto

import kotlinx.serialization.Serializable

@Serializable
data class WordDto (
    val title: String,
    val wordId: String
)