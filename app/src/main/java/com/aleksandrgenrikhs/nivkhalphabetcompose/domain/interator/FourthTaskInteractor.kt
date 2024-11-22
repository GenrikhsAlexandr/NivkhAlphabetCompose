package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import androidx.annotation.VisibleForTesting
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FourthTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class FourthTaskInteractor
@Inject constructor(
    private val repository: AlphabetRepository
) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val usedWords: MutableSet<WordModel> = mutableSetOf()

    suspend fun getWordsForFourthTask(letterId: String): FourthTaskModel {
        val filterWords =
            repository.getWords()[letterId] ?: error("Can't find words for letter $letterId")
        val currentWord =
            filterWords.filterNot { usedWords.contains(it) }
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
