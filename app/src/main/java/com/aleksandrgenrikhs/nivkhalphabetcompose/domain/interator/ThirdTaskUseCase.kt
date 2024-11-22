package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class ThirdTaskUseCase
@Inject constructor(
    private val repository: AlphabetRepository
) {

    suspend fun getWordsForThirdTask(letterId: String): List<WordModel> {
        val letterWords =
            repository.getWords()[letterId] ?: error("Can't find words for letter $letterId")
        return letterWords
    }
}
