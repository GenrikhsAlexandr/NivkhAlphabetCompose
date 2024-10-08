package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.PrefRepository
import javax.inject.Inject

class AlphabetInteractor
@Inject constructor(
    private val alphabetRepository: AlphabetRepository,
    private val sharedPreferencesRepository: PrefRepository
) {

    suspend fun getWords(): Map<String, List<WordModel>> = alphabetRepository.getWords()

    fun taskCompleted(taskId: Int, letterId: String) =
        sharedPreferencesRepository.taskCompleted(taskId, letterId)

    suspend fun isTaskCompleted(taskId: Int, letterId: String): Boolean =
        sharedPreferencesRepository.isTaskCompleted(taskId, letterId)

    suspend fun getLetterCompleted(taskId: Int): List<Letters>? =
        sharedPreferencesRepository.getLetterCompleted(taskId)

    fun saveName(name: String) = sharedPreferencesRepository.saveName(name)

    suspend fun getName(): String = sharedPreferencesRepository.getName()
}