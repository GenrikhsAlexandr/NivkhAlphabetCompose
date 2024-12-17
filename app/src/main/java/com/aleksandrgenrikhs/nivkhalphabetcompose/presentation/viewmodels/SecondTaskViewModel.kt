package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.SecondTaskInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateSecondTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondTaskViewModel
@Inject constructor(
    private val secondInteractor: SecondTaskInteractor,
    private val prefInteractor: PrefInteractor,
    private val uiStateMapper: UIStateSecondTaskMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<SecondTaskUIState> =
        MutableStateFlow(SecondTaskUIState())
    val uiState = _uiState.asStateFlow()

    fun setSelectedLetter(letter: String) {
        _uiState.update { state ->
            state.copy(selectedLetter = letter)
        }
    }

    init {
        viewModelScope.launch {
            val currentValue = mediaPlayerInteractor.getIsSoundEnable()
            _uiState.update { state ->
                state.copy(shouldPlayFinishAudio = currentValue)
            }
        }
    }

    fun updateWordsForLetter(letterId: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                val filteredWords = secondInteractor.getWordsForSecondTask(letterId)
                val mappedWords = uiStateMapper.map(filteredWords)
                with(mappedWords) {
                    state.copy(
                        lettersId = lettersId,
                        wordsId = wordsId,
                        icons = icons,
                        isFlipped = isFlipped,
                        titles = titles,
                        isCorrectAnswers = isCorrectAnswers,
                        isCorrectWord = false
                    )
                }
            }
        }
    }

    fun flipCard(wordId: String, letterId: String) {
        _uiState.update { state ->
            if (!state.wordsId.contains(wordId)) return@update state

            val isCorrectWord = letterId == state.selectedLetter
            val correctAnswersCount = if (isCorrectWord) {
                state.correctAnswersCount + 1
            } else {
                state.correctAnswersCount
            }
            val isCompleted = correctAnswersCount == 3
            val index = state.wordsId.indexOfFirst { it == wordId }
            val newIsFlippedList = state.isFlipped.toMutableList()
            newIsFlippedList[index] = !newIsFlippedList[index]
            val newIsCorrectAnswerList = state.isCorrectAnswers.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectWord
            state.copy(
                isFlipped = newIsFlippedList,
                isCorrectAnswers = newIsCorrectAnswerList,
                correctAnswersCount = correctAnswersCount,
                isCompleted = isCompleted,
                isCorrectWord = isCorrectWord,
            )
        }
        if (uiState.value.isCorrectWord) {
            playSound("$WORDS_AUDIO$wordId")
        } else {
            playSound(ERROR_AUDIO)
        }
        processCorrectGuess()
        saveTaskProgress()
    }

    private fun processCorrectGuess() {
        if (uiState.value.isCorrectWord && !uiState.value.isCompleted) {
            viewModelScope.launch {
                delay(2000)
                updateWordsForLetter(uiState.value.selectedLetter)
            }
        }
    }

    private fun saveTaskProgress() {
        if (uiState.value.isCompleted) {
            prefInteractor.taskCompleted(Task.SECOND.stableId, uiState.value.selectedLetter)
        }
    }

    fun playSound(url: String) {
        if (url == FINISH_AUDIO) {
            _uiState.update { state ->
                state.copy(
                    shouldPlayFinishAudio = false
                )
            }
        }
        mediaPlayerInteractor.playerDestroy()
        mediaPlayerInteractor.initPlayer(context, url)
    }

    override fun onCleared() {
        super.onCleared()
        secondInteractor.clearPreviousWordsList()
        mediaPlayerInteractor.playerDestroy()
    }
}