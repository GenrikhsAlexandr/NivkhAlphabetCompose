package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.listtasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel
@Inject constructor() : ViewModel() {

    private val _tasks = mutableStateOf(Task.entries)
    val tasks get() = _tasks
}