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

    suspend fun saveIsCertificateCreated(value: Boolean) {
        val currentValue = sharedPreferencesRepository.getCertificateStatus()
        if (!currentValue) {
            sharedPreferencesRepository.saveIsCertificateCreated(value)
        }
    }

    suspend fun getCertificateStatus(): Boolean = sharedPreferencesRepository.getCertificateStatus()

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
            if (differenceInMillis < 1) {
                sharedPreferencesRepository.saveTimeLearningAlphabet(1)
            } else {
                sharedPreferencesRepository.saveTimeLearningAlphabet(differenceInMillis.toInt())
            }
        }
    }

    suspend fun getTimeLearning(): Int = sharedPreferencesRepository.getTimeLearningAlphabet()

    suspend fun saveSoundEnabled(value: Boolean) {
        sharedPreferencesRepository.saveSoundEnabled(value)
    }

    suspend fun getSoundEnabled(): Boolean = sharedPreferencesRepository.getSoundEnabled()
}
