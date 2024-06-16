package com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters

interface PrefRepository {

    fun taskCompleted(taskId: Int, letterId: String)

    fun isTaskCompleted(taskId: Int, letterId: String): Boolean

    fun getLetterCompleted(taskId: Int): List<Letters>?
}