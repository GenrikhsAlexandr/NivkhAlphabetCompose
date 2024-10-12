package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.PrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefRepositoryImpl
@Inject constructor(@ApplicationContext private val context: Context) : PrefRepository {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            "AppPref",
            Context.MODE_PRIVATE
        )
    }

    override fun taskCompleted(taskId: Int, letterId: String) {
        val currentValue = preferences.getString(taskId.toString(), "")
        val letters = currentValue?.split(":") ?: emptyList()
        if (!letters.contains(letterId)) {
            preferences.edit()
                .putString(
                    taskId.toString(), "$currentValue:$letterId"
                )
                .apply()
        }
    }

    override suspend fun getLetterCompleted(taskId: Int): List<Letters>? {
        val completedLetter = preferences.getString(
            taskId.toString(), ""
        )
        return completedLetter?.split(":")?.mapNotNull {
            Letters.getById(it)
        }
    }

    override fun saveIsCertificateCreated(value: Boolean) {
        preferences.edit()
            .putBoolean("fullName", value)
            .apply()
    }

    override suspend fun getCertificateStatus(): Boolean {
        return preferences.getBoolean("fullName", false)
    }

    override fun saveStartTimeLearningAlphabet() {
        val time = System.currentTimeMillis()
        preferences.edit()
            .putLong("startTime", time)
            .apply()
    }

    override suspend fun getStartTimeLearningAlphabet(): Long =
        preferences.getLong("startTime", 0L)

    override suspend fun saveTimeLearningAlphabet(time: Int) {
        preferences.edit()
            .putInt("timeLearningAlphabet", time)
            .apply()
    }

    override suspend fun getTimeLearningAlphabet(): Int =
        preferences.getInt("timeLearningAlphabet", 0)

    override suspend fun isTaskCompleted(taskId: Int, letterId: String): Boolean {
        val completedLetters = getLetterCompleted(taskId)
        return completedLetters?.contains(Letters.getById(letterId)) ?: false
    }
}