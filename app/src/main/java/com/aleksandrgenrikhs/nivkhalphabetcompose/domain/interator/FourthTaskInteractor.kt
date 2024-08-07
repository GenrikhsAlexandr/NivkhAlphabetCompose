package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FourthTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class FourthTaskInteractor
@Inject constructor(
    private val repository: AlphabetRepository
) {

    private val usedWords: MutableSet<WordModel> = mutableSetOf()

    suspend fun getWordsForFourthTask(letterId: String): FourthTaskModel {
        val filterWords = repository.getWords()[letterId] ?: error("Not found filterWords")
        val currentWord =
            filterWords.minus(usedWords)
                .shuffled()
                .first()
        usedWords.add(currentWord)
        return FourthTaskModel(
            title = currentWord.title,
            icon = currentWord.icon,
            wordId = currentWord.wordId
        )
    }

    fun clearPreviousWordsList() {
        usedWords.clear()
    }
}
