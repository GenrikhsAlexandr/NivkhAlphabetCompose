package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.FirstTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class FirstTaskInteractor
@Inject constructor(
    private val repository: AlphabetRepository,
    private val mapper: FirstTaskMapper
) {
    suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel> {
        val words = repository.getWords()
        val letterWords = words[letterId] ?: error("Not found letterWords")
        return mapper.map(letterWords)
    }
}
