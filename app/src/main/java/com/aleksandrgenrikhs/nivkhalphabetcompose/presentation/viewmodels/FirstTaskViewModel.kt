package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
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
    val interactor: AlphabetInteractor,
    val mapper: UIStateFirstTaskMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<FirstTaskUIState> = MutableStateFlow(FirstTaskUIState())
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
        val newListWord = mapper.map(listWords)
        if (listWords.isNotEmpty()) {
            _uiState.update {
                with(newListWord) {
                    _uiState.value.copy(
                        title = title,
                        wordId = wordId,
                        icon = icon,
                        progressWord = progressWord,
                        isClickableWord = isClickableWord,
                        isCompletedWord = isCompletedWord,
                        getWordError = false
                    )
                }
            }
        } else {
            _uiState.update {
                _uiState.value.copy(
                    getWordError = true,
                )
            }
        }
    }

    suspend fun isTaskCompleted(letter: String) {
        _uiState.update { uiState ->
            val isTaskCompleted = interactor.isTaskCompleted(
                Task.FIRST.stableId,
                letter
            )
            uiState.copy(
                isVisibleWord = isTaskCompleted,
            )
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
                                || !it && uiState.value.isCompletedWord[0]
                                || !it && uiState.value.isCompletedWord[1] -> interactor.playerDestroy()

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onClickElement(element: String, index: Int?) {
        playSound(element)
        isPlaying()
        when (element) {
            uiState.value.selectedLetter -> {
                _uiState.update { uiState ->
                    val newProgressLetter = uiState.progressLetter + 1
                    val newIsVisibleWord = newProgressLetter > 4 || uiState.isVisibleWord
                    val newIsCompletedLetter = newProgressLetter > 4
                    val newIsClickable = uiState.isClickableWord.toMutableList()
                    if (uiState.title.isNotEmpty()) {
                        if (newIsCompletedLetter) {
                            newIsClickable[0] = true
                        }
                    }
                    uiState.copy(
                        progressLetter = newProgressLetter,
                        isVisibleWord = newIsVisibleWord,
                        isClickableWord = newIsClickable,
                        isCompletedLetter = newIsCompletedLetter
                    )
                }
            }

            else -> {
                if (index == null) return
                _uiState.update { uiState ->
                    val newProgressWord = uiState.progressWord.toMutableList()
                    val newWordIsCompleted = uiState.isCompletedWord.toMutableList()
                    val newIsClickable = uiState.isClickableWord.toMutableList()
                    newProgressWord[index] = uiState.progressWord[index] + 1
                    newWordIsCompleted[index] = newProgressWord[index] > 2
                    if (newWordIsCompleted[index] && index < uiState.title.lastIndex) {
                        newIsClickable[index + 1] = true
                    }
                    uiState.copy(
                        progressWord = newProgressWord,
                        isCompletedWord = newWordIsCompleted,
                        isClickableWord = newIsClickable,
                    )
                }
                if (uiState.value.isCompletedWord.last()) {
                    interactor.taskCompleted(Task.FIRST.stableId, uiState.value.selectedLetter)
                }
            }
        }
    }

    fun playSound(element: String) {
        when (element) {
            uiState.value.selectedLetter -> {
                interactor.initPlayer("$LETTER_AUDIO${element}")
                interactor.play()
            }

            FINISH_AUDIO -> {
                interactor.initPlayer(FINISH_AUDIO)
                interactor.play()
                _uiState.update { uiState ->
                    uiState.copy(
                        isFinishAudio = true
                    )
                }
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