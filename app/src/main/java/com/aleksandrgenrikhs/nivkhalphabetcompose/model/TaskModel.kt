package com.aleksandrgenrikhs.nivkhalphabetcompose.model

import com.aleksandrgenrikhs.nivkhalphabetcompose.Task

data class TaskModel(
    val task: Task,
    val isTaskCompleted: Boolean,
    val isNextTaskVisible:Boolean
)