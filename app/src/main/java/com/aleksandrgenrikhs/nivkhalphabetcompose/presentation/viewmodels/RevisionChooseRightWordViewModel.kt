package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.RevisionChooseRightWordUseCase
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateRevisionChooseRightWordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionChooseRightWordUIState
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
class RevisionChooseRightWordViewModel
@Inject constructor(
    private val chooseRightWordUseCase: RevisionChooseRightWordUseCase,
    private val uiStateMapper: UIStateRevisionChooseRightWordMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionChooseRightWordUIState> =
        MutableStateFlow(RevisionChooseRightWordUIState())
    val uiState = _uiState.asStateFlow()
    private var taskCompleted = false

    suspend fun updateWords() {
        _uiState.update { state ->
            val words = chooseRightWordUseCase.getWordsForRevisionChooseRightWord()
            val mappedWords = uiStateMapper.map(words)
            with(mappedWords) {
                state.copy(
                    correctWordId = correctWordId,
                    wordsId = wordsId,
                    title = title,
                    icon = icon,
                    isCorrectAnswers = isCorrectAnswers,
                    isUserAnswerCorrect = false
                )
            }
        }
    }

    fun checkUserGuess(wordId: String) {
        val isCorrectAnswer = wordId == uiState.value.correctWordId
        _uiState.update { state ->
            val correctAnswersCount = if (isCorrectAnswer) {
                state.correctAnswersCount + 1
            } else {
                state.correctAnswersCount
            }
            taskCompleted = correctAnswersCount == 5
            val index = state.wordsId.indexOfFirst { it == wordId }
            val newIsCorrectAnswerList = state.isCorrectAnswers.toMutableList()
            newIsCorrectAnswerList[index] = isCorrectAnswer
            state.copy(
                isCorrectAnswers = newIsCorrectAnswerList,
                isUserAnswerCorrect = isCorrectAnswer,
                correctAnswersCount = correctAnswersCount
            )
        }
        if (isCorrectAnswer) {
            playSound("$WORDS_AUDIO$wordId")
            processCorrectGuess()
        } else {
            playSound(ERROR_AUDIO)
        }
    }

    private fun processCorrectGuess() {
        if (!taskCompleted) {
            viewModelScope.launch {
                delay(1500)
                updateWords()
            }
        } else {
            showDialog()
        }
    }

    private fun showDialog() {
        viewModelScope.launch {
            delay(1500)
            if (mediaPlayerInteractor.getIsSoundEnable()) {
                playSound(FINISH_AUDIO)
            }
            _uiState.update { state ->
                state.copy(
                    showDialog = true,
                )
            }
        }
    }

    fun playSound(url: String) {
        mediaPlayerInteractor.playerDestroy()
        mediaPlayerInteractor.initPlayer(context, url)
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerInteractor.playerDestroy()
    }
}
