package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.SecondTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import javax.inject.Inject

class SecondTaskInteractor
@Inject
constructor(
    private val mapper: SecondTaskMapper,
    private val repository: AlphabetRepository,
) {

    private val usedWords: MutableSet<WordModel> = mutableSetOf()

    suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel> {
        val words = repository.getWords()
        val letterWords = words[letterId] ?: error("Not found letterWords")
        val otherWords = words.minus(letterId)
            .values
            .flatten()
        val correctWord = letterWords.minus(usedWords)
            .shuffled()
            .first()
        val wrongWords = selectUniqueRandomElements(otherWords, 2)
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