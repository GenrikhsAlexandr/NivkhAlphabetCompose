package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.FirstTaskUseCase
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateFirstTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.FirstTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
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
    private val alphabetInteractor: AlphabetInteractor,
    private val firstTaskUseCase: FirstTaskUseCase,
    private val uiStateMapper: UIStateFirstTaskMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<FirstTaskUIState> = MutableStateFlow(FirstTaskUIState())
    val uiState = _uiState.asStateFlow()

    fun setSelectedLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(
                selectedLetter = letter,
            )
        }
    }

    suspend fun updateWordsForLetter(letterId: String) {
        val letteredWords = firstTaskUseCase.getWordsForFirstTask(letterId)
        val mappedWords = uiStateMapper.map(letteredWords)
        _uiState.update {
            with(mappedWords) {
                _uiState.value.copy(
                    titles = titles,
                    wordsId = wordsId,
                    icons = icons,
                    progressWords = progressWords,
                    isClickableWords = isClickableWords,
                    isCompletedWords = isCompletedWords,
                )
            }
        }
    }

    suspend fun checkTaskCompletion(letter: String) {
        _uiState.update { state ->
            val isTaskCompleted = alphabetInteractor.isTaskCompleted(
                Task.FIRST.stableId,
                letter
            )
            state.copy(
                isVisibleWord = isTaskCompleted,
            )
        }
    }

    private fun updatePlayingState() {
        _uiState.update { state ->
            state.copy(
                isPlaying = true
            )
        }
        viewModelScope.launch {
            mediaPlayerInteractor.isPlaying().collect {
                _uiState.update { state ->
                    state.copy(
                        isPlaying = it
                    )
                }
                when {
                    !it && uiState.value.isCompletedLetter
                            || !it && uiState.value.isCompletedWords[0]
                            || !it && uiState.value.isCompletedWords[1] -> mediaPlayerInteractor.playerDestroy()
                }
            }
        }
    }

    fun onClickElement(element: String, index: Int?) {
        playSoundForElement(element)
        updatePlayingState()
        when (element) {
            uiState.value.selectedLetter -> {
                _uiState.update { state ->
                    val newProgressLetter = state.progressLetter + 1
                    val newIsVisibleWord = newProgressLetter > 4 || state.isVisibleWord
                    val newIsCompletedLetter = newProgressLetter > 4
                    val newIsClickable = state.isClickableWords.toMutableList()
                    if (newIsCompletedLetter) {
                        newIsClickable[0] = true
                    }
                    state.copy(
                        progressLetter = newProgressLetter,
                        isVisibleWord = newIsVisibleWord,
                        isClickableWords = newIsClickable,
                        isCompletedLetter = newIsCompletedLetter
                    )
                }
            }

            else -> {
                if (index == null) return
                _uiState.update { state ->
                    val newProgressWord = state.progressWords.toMutableList()
                    val newWordIsCompleted = state.isCompletedWords.toMutableList()
                    val newIsClickable = state.isClickableWords.toMutableList()
                    newProgressWord[index] = state.progressWords[index] + 1
                    newWordIsCompleted[index] = newProgressWord[index] > 2
                    if (newWordIsCompleted[index] && index < state.titles.lastIndex) {
                        newIsClickable[index + 1] = true
                    }
                    state.copy(
                        progressWords = newProgressWord,
                        isCompletedWords = newWordIsCompleted,
                        isClickableWords = newIsClickable,
                    )
                }
                saveTaskProgress()
            }
        }
    }

    private fun saveTaskProgress() {
        if (uiState.value.isCompletedWords.last()) {
            alphabetInteractor.taskCompleted(
                Task.FIRST.stableId,
                uiState.value.selectedLetter
            )
        }
    }

    fun playSoundForElement(element: String) {
        when (element) {
            uiState.value.selectedLetter -> {
                mediaPlayerInteractor.initPlayer(context, "$LETTER_AUDIO$element")
            }

            FINISH_AUDIO -> {
                mediaPlayerInteractor.initPlayer(context, FINISH_AUDIO)
                _uiState.update { state ->
                    state.copy(
                        isFinishAudio = true
                    )
                }
            }

            else -> {
                mediaPlayerInteractor.initPlayer(context, "$WORDS_AUDIO$element")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerInteractor.playerDestroy()
    }
}