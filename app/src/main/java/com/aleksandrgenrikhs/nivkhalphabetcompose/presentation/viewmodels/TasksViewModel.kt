package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
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
    private val interactor: AlphabetInteractor,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskUIState> = MutableStateFlow(
        TaskUIState(
            titleResId = Task.entries.map { it.titleResId },
            iconId = Task.entries.map { it.icon },
            stableId = Task.entries.map { it.stableId },
            route = Task.entries.map { it.route },
            isNetworkConnected = interactor.isNetWorkConnected()
        )
    )
    val uiState = _uiState.asStateFlow()

    suspend fun isTaskCompleted(letter: String) {
        _uiState.update { uiState ->
            val newIsTaskCompleted = uiState.isTaskCompleted.toMutableList()
            val newIsNextTaskVisible = uiState.isNextTaskVisible.toMutableList()
            uiState.stableId.mapIndexed { index, id ->
                val isTaskCompleted = interactor.isTaskCompleted(id, letter)
                newIsTaskCompleted[index] = isTaskCompleted

                if (newIsTaskCompleted[index] && index < uiState.stableId.lastIndex) {
                    newIsNextTaskVisible[index + 1] = true
                } else {
                    newIsNextTaskVisible[0] = true
                }
            }
            uiState.copy(
                isTaskCompleted = newIsTaskCompleted,
                isNextTaskVisible = newIsNextTaskVisible
            )
        }
    }
}