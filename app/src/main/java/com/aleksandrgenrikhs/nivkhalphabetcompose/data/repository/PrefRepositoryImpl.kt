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

    override fun saveName(name: String) {
        val currentValue = preferences.getString("fullName", "")
        if (!currentValue.isNullOrEmpty() && currentValue != name) {
            preferences.edit()
                .remove("fullName")
                .apply()
        }
        preferences.edit()
            .putString("fullName", name)
            .apply()
    }

    override suspend fun getName(): String {
        return preferences.getString("fullName", "") ?: ""
    }

    override suspend fun isTaskCompleted(taskId: Int, letterId: String): Boolean {
        val completedLetters = getLetterCompleted(taskId)
        return completedLetters?.contains(Letters.getById(letterId)) ?: false
    }
}