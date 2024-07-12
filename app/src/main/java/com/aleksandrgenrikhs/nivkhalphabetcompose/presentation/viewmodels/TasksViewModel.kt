package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.TaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TasksViewModel
@Inject constructor(
    private val interactor: AlphabetInteractor
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskUIState> = MutableStateFlow(TaskUIState(
        task = Task.entries.map {
            TaskModel(
                task = it,
                isTaskCompleted = false,
                isNextTaskVisible = false
            )
        },
        isNetworkConnected = interactor.isNetWorkConnected()
    ))
    val uiState = _uiState.asStateFlow()

    fun isTaskCompleted(letter: String) {
        _uiState.update { uiState ->
            val taskList = uiState.task.toMutableList()
            uiState.task.mapIndexed { index, task ->
                val isTaskCompleted = interactor.isTaskCompleted(
                    task.task.stableId,
                    letter
                )
                println("isTaskCompleted =$isTaskCompleted")
                if (isTaskCompleted && index < uiState.task.lastIndex) {
                    println("isNextTaskVisible = ${taskList[index + 1]}")
                    taskList[index + 1] = taskList[index + 1].copy(
                        isNextTaskVisible = true,
                    )
                } else {
                    taskList[0] = taskList[0].copy(
                        isNextTaskVisible = true,
                    )
                }
            }
            uiState.copy(
                task = taskList,
            )
        }
    }
}