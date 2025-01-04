package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.TaskLearnLetterMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.TaskLearnLetterModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import javax.inject.Inject

class TaskLearnLetterUseCase
@Inject constructor(
    private val repository: AlphabetRepository,
    private val mapper: TaskLearnLetterMapper,
) {
    suspend fun getWordsForTaskLearnLetter(letterId: String): List<TaskLearnLetterModel> {
        val words = repository.getWords()
        val letterWords = words[letterId] ?: error("Can't find words for letter $letterId")
        return mapper.map(letterWords)
    }
}
