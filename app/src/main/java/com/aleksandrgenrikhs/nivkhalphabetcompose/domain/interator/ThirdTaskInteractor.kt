package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class ThirdTaskInteractor
@Inject constructor(
    private val repository: AlphabetRepository
) {

    suspend fun getWordsForThirdTask(letterId: String): List<WordModel> {
        val letterWords = repository.getWords()[letterId] ?: error("Not found letterWords")
        return letterWords
    }
}
