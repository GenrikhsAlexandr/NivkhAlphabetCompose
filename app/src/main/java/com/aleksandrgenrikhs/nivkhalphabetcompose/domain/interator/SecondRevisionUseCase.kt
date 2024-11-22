package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.SecondRevisionMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.SelectUniqueElements
import javax.inject.Inject

class SecondRevisionUseCase
@Inject constructor(
    private val repository: AlphabetRepository,
    private val revisionSecondMapper: SecondRevisionMapper,
    private val selectUniqueElements: SelectUniqueElements
) {

    suspend fun getWordsForRevisionSecond(): List<SecondRevisionModel> {
        val words = repository.getWords()
            .values
            .flatten()
        val selectedWords = selectUniqueElements(words, 4) { it.letterId }
        return revisionSecondMapper.map(selectedWords)
    }
}
