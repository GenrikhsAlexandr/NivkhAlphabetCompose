package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters

interface PrefRepository {

    fun taskCompleted(taskId: Int, letterId: String)

    suspend fun isTaskCompleted(taskId: Int, letterId: String): Boolean

    suspend fun getLetterCompleted(taskId: Int): List<Letters>?
}