package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.FirstTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class FirstTaskUseCase
@Inject constructor(
    private val repository: AlphabetRepository,
    private val mapper: FirstTaskMapper
) {
    suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel> {
        val words = repository.getWords()
        val letterWords = words[letterId] ?: error("Can't find words for letter $letterId")
        return mapper.map(letterWords)
    }
}
