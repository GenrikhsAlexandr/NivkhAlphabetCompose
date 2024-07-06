package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.FirstTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTER_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstTaskViewModel
@Inject constructor(
    val interactor: AlphabetInteractor,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FirstTaskUIState> = MutableStateFlow(
        FirstTaskUIState(
            isNetworkConnected = interactor.isNetWorkConnected()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun setLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(
                selectedLetter = letter,
            )
        }
    }

    suspend fun getWords(letterId: String) {
        val listWords = interactor.getWordsForFirstTask(letterId)
        if (listWords.isNotEmpty()) {
            _uiState.update {
                _uiState.value.copy(
                    words = listWords,
                    getWordError = false
                )
            }
        } else {
            _uiState.update {
                _uiState.value.copy(
                    getWordError = true,
                )
            }
        }
    }

    private fun isPlaying() {
        _uiState.update { uiState ->
            uiState.copy(
                isPlaying = true
            )
        }
        viewModelScope.launch {
            try {
                interactor.isPlaying().collect {
                    _uiState.update { uiState ->
                        uiState.copy(
                            isPlaying = it
                        )
                    }
                    when {
                        !it && uiState.value.isCompletedLetter
                                || !it && uiState.value.words[0].isCompleted
                                || !it && uiState.value.words[1].isCompleted -> interactor.playerDestroy()

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onClickElement(element: String) {
        playSound(element)
        isPlaying()
        when (element) {
            uiState.value.selectedLetter -> {
                _uiState.update { uiState ->
                    val newProgressLetter = uiState.progressLetter + 1
                    val isVisibleWord = newProgressLetter == 5
                    val newIsClickableLetter = newProgressLetter < 5
                    val newWordsList = uiState.words.toMutableList().apply {
                        if (uiState.words.isNotEmpty()) {
                            if (isVisibleWord) {
                                this[0] = this[0].copy(
                                    isClickable = true,
                                )
                            }
                        }
                    }
                    uiState.copy(
                        progressLetter = newProgressLetter,
                        isVisibleWord = isVisibleWord,
                        isClickableLetter = newIsClickableLetter,
                        words = newWordsList,
                        isCompletedLetter = isVisibleWord
                    )
                }
            }

            uiState.value.words[0].wordId -> {
                _uiState.update { uiState ->
                    val newProgressWord = uiState.words[0].progress + 1
                    val isCompleted = newProgressWord == 3
                    val newIsClickableWord1 = newProgressWord < 3
                    val newWordsList = uiState.words.toMutableList().apply {
                        this[0] = this[0].copy(
                            isClickable = newIsClickableWord1,
                            progress = newProgressWord,
                            isCompleted = isCompleted
                        )
                        if (isCompleted) {
                            this[1] = this[1].copy(
                                isClickable = true,
                            )
                        }
                    }
                    uiState.copy(
                        words = newWordsList,
                    )
                }
            }

            uiState.value.words[1].wordId -> {
                _uiState.update { uiState ->
                    val newProgressWord = uiState.words[1].progress + 1
                    val isCompleted = newProgressWord == 3
                    val newIsClickableWord2 = newProgressWord < 3
                    val newWordsList = uiState.words.toMutableList().apply {
                        this[1] = this[1].copy(
                            isClickable = newIsClickableWord2,
                            progress = newProgressWord,
                            isCompleted = isCompleted
                        )
                        if (isCompleted) {
                            this[2] = this[2].copy(
                                isClickable = true,
                            )
                        }
                    }
                    uiState.copy(
                        words = newWordsList,
                    )
                }
            }

            uiState.value.words[2].wordId -> {
                _uiState.update { uiState ->
                    val newProgressWord = uiState.words[2].progress + 1
                    val isCompleted = newProgressWord == 3
                    val newIsClickableWord3 = newProgressWord < 3
                    if (isCompleted) {
                        interactor.taskCompleted(Task.FIRST.stableId, uiState.selectedLetter)
                    }
                    val newWordsList = uiState.words.toMutableList().apply {
                        this[2] = this[2].copy(
                            isClickable = newIsClickableWord3,
                            progress = newProgressWord,
                            isCompleted = isCompleted
                        )
                    }
                    uiState.copy(
                        words = newWordsList,
                        isCompleted = isCompleted,
                    )
                }
            }
        }
    }

    private fun playSound(element: String) {
        when {
            element == uiState.value.selectedLetter -> {
                interactor.initPlayer("$LETTER_AUDIO${element}")
                interactor.play()
            }

            else -> {
                interactor.initPlayer("$WORDS_AUDIO$element")
                interactor.play()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        interactor.playerDestroy()
    }
}