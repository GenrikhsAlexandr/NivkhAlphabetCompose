package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.PrefRepository
import javax.inject.Inject

class PrefInteractor
@Inject constructor(
    private val sharedPreferencesRepository: PrefRepository
) {
    fun taskCompleted(taskId: Int, letterId: String) =
        sharedPreferencesRepository.taskCompleted(taskId, letterId)

    suspend fun isTaskCompleted(taskId: Int, letterId: String): Boolean =
        sharedPreferencesRepository.isTaskCompleted(taskId, letterId)

    suspend fun getLetterCompleted(taskId: Int): List<Letters>? =
        sharedPreferencesRepository.getLetterCompleted(taskId)

    suspend fun saveName(name: String) {
        val currentValue = sharedPreferencesRepository.getName()
        if (currentValue.isNotEmpty()) {
            sharedPreferencesRepository.saveName(name)
        }
    }

    suspend fun getName(): String = sharedPreferencesRepository.getName()

    suspend fun saveStartTimeLearning() {
        val currentValue = sharedPreferencesRepository.getStartTimeLearningAlphabet()
        if (currentValue == 0L) {
            sharedPreferencesRepository.saveStartTimeLearningAlphabet()
        }
    }

    suspend fun saveTimeLearning() {
        val currentValue = sharedPreferencesRepository.getTimeLearningAlphabet()
        if (currentValue == 0) {
            val timeEnd = System.currentTimeMillis()
            val millisInDay = 24 * 60 * 60 * 1000
            val differenceInMillis =
                (timeEnd - sharedPreferencesRepository.getStartTimeLearningAlphabet()) / millisInDay
            sharedPreferencesRepository.saveTimeLearningAlphabet(differenceInMillis.toInt())
        }
    }

    suspend fun getTimeLearning(): Int = sharedPreferencesRepository.getTimeLearningAlphabet()
}