package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueElements
import javax.inject.Inject

class RevisionCompleteTableInteractor
@Inject constructor(
    private val repository: AlphabetRepository,
) {

    suspend fun getWordsForRevisionCompleteTable(): List<WordModel> {
        val allWords = repository.getWords()
            .values
            .flatten()
        return selectUniqueElements(allWords, 3) { it.letterId }
    }
}
