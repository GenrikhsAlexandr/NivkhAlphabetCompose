package com.aleksandrgenrikhs.nivkhalphabetcompose.domain

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel

fun createWords(): Map<String, List<WordModel>> {
    return mapOf(
        "a" to listOf(
            createWordModel(letterId = "a", wordId = "1"),
            createWordModel(letterId = "a", wordId = "2"),
            createWordModel(letterId = "a", wordId = "3"),
        ),
        "b" to listOf(
            createWordModel(letterId = "b", wordId = "1"),
            createWordModel(letterId = "b", wordId = "2"),
            createWordModel(letterId = "b", wordId = "3"),
        ),
        "c" to listOf(
            createWordModel(letterId = "c", wordId = "1"),
            createWordModel(letterId = "c", wordId = "2"),
            createWordModel(letterId = "c", wordId = "3"),
        ),
        "d" to listOf(
            createWordModel(letterId = "d", wordId = "1"),
            createWordModel(letterId = "d", wordId = "2"),
            createWordModel(letterId = "d", wordId = "3"),
        ),
        "f" to listOf(
            createWordModel(letterId = "f", wordId = "1"),
            createWordModel(letterId = "f", wordId = "2"),
            createWordModel(letterId = "f", wordId = "3"),
        ),
    )
}

fun createWordModel(letterId: String, wordId: String): WordModel {
    return WordModel(
        letterId = "letterId $letterId",
        title = "title $wordId",
        wordId = "wordId $letterId $wordId",
        icon = "iconUrl"
    )
}
