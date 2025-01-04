package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import androidx.annotation.VisibleForTesting
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.TaskFindWordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.TaskFindWordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueElements
import javax.inject.Inject

class TaskFindWordInteractor
@Inject
constructor(
    private val mapper: TaskFindWordMapper,
    private val repository: AlphabetRepository,
) {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val usedWords: MutableSet<WordModel> = mutableSetOf()

    suspend fun getWordsForTaskMatchWords(letterId: String): List<TaskFindWordModel> {
        val words = repository.getWords()
        val letterWords = words[letterId] ?: error("Can't find words for letter $letterId")
        val otherWords = words
            .values
            .flatten()
            .filterNot { letterWords.contains(it) }
            .filterNot { usedWords.contains(it) }
        val correctWord = letterWords.filterNot { usedWords.contains(it) }
            .shuffled()
            .first()
        val wrongWords = selectUniqueElements(otherWords, 2) { it.letterId }
        val resultList = buildList {
            addAll(wrongWords)
            add(correctWord)
        }
        usedWords.addAll(resultList)
        return mapper.map(resultList)
            .shuffled()
    }

    fun clearPreviousWordsList() {
        usedWords.clear()
    }
}
