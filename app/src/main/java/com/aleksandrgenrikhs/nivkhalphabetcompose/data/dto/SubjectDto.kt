package com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubjectDto(
    val id: String,
    val words: List<WordDto>,
)
