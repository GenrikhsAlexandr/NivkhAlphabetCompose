package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.SecondRevisionMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import javax.inject.Inject

class SecondRevisionUseCase
@Inject constructor(
    private val repository: AlphabetRepository,
    private val revisionSecondMapper: SecondRevisionMapper,
) {

    suspend fun getWordsForRevisionSecond(): List<SecondRevisionModel> {
        val words = repository.getWords()
            .values
            .flatten()
        val selectedWords = selectUniqueRandomElements(words, 4)
        return revisionSecondMapper.map(selectedWords)
    }
}
