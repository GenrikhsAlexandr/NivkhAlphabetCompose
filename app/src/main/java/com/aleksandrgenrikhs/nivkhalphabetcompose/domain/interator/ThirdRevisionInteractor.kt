package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class ThirdRevisionInteractor
@Inject constructor(
    private val repository: AlphabetRepository,
) {

    suspend fun getWordsForRevisionThird(): List<WordModel> {
        val allWords = repository.getWords()
            .values
            .flatten()
        return selectUniqueWordsByLetterId(allWords)
    }

    private fun selectUniqueWordsByLetterId(words: List<WordModel>): List<WordModel> {
        return words.shuffled()
            .distinctBy { it.letterId }
            .take(3)
    }
}
