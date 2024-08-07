package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import javax.inject.Inject

class RevisionThirdInteractor
@Inject constructor(
    private val repository: AlphabetRepository,
) {

    suspend fun getWordsForRevisionThird(): List<WordModel> {
        val allWords = repository.getWords()
            .values
            .flatten()
        return selectUniqueRandomElements<WordModel>(allWords, 3)
    }
}
