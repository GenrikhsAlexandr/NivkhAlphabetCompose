package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.SelectUniqueElements
import javax.inject.Inject

class ThirdRevisionInteractor
@Inject constructor(
    private val repository: AlphabetRepository,
    private val selectUniqueElements: SelectUniqueElements
) {

    suspend fun getWordsForRevisionThird(): List<WordModel> {
        val allWords = repository.getWords()
            .values
            .flatten()
        return selectUniqueElements(allWords, 3) { it.letterId }
    }
}
