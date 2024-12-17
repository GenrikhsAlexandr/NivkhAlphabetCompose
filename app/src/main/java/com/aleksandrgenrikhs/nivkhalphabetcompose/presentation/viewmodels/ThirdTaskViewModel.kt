package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.ClipData
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.ThirdTaskUseCase
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateThirdTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.ThirdTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThirdTaskViewModel
@Inject constructor(
    private val prefInteractor: PrefInteractor,
    private val thirdUseCase: ThirdTaskUseCase,
    private val uiStateMapper: UIStateThirdTaskMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<ThirdTaskUIState> = MutableStateFlow(ThirdTaskUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentValue = mediaPlayerInteractor.getIsSoundEnable()
            _uiState.update { state ->
                state.copy(shouldPlayFinishAudio = currentValue)
            }
        }
    }

    fun setSelectedLetter(letter: String) {
        _uiState.update { state ->
            state.copy(selectedLetter = letter)
        }
    }

    suspend fun updateWordsForLetter(letterId: String) {
        _uiState.update { state ->
            val filteredWords = thirdUseCase.getWordsForThirdTask(letterId)
            val mappedWords = uiStateMapper.map(filteredWords)
            with(mappedWords) {
                state.copy(
                    titles = titles,
                    wordsId = wordsId,
                    icons = icons,
                    shareableWords = shareableWords,
                )
            }
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

    fun updateReceivingContainer(clipData: ClipData?, index: Int) {
        if (clipData == null) return
        val sharedText = clipData.getItemAt(0).text.toString()
        val newShareWords = uiState.value.shareableWords.toMutableList()
        val sharedTextIndex = newShareWords.indexOf(sharedText)
        _uiState.update { state ->
            val newCurrentWords = state.currentWords.toMutableList()
            if (sharedTextIndex != -1 && newCurrentWords[index] == null) {
                newShareWords[sharedTextIndex] = null
                newCurrentWords[index] = sharedText
            }
            state.copy(
                currentWords = newCurrentWords,
                shareableWords = newShareWords,
                isGuessWrong = false
            )
        }
    }

    fun resetState() {
        _uiState.update { state ->
            state.copy(
                currentWords = mutableListOf(null, null, null),
                isGuessWrong = false,
                shareableWords = uiState.value.titles.shuffled()
            )
        }
    }

    fun checkAnswer() {
        val currentWords = uiState.value.currentWords
        val correctWords = uiState.value.titles
        if (currentWords == correctWords) {
            _uiState.update { state ->
                state.copy(
                    isAnswerCorrect = true
                )
            }
            prefInteractor.taskCompleted(Task.THIRD.stableId, uiState.value.selectedLetter)
        } else {
            playSound(ERROR_AUDIO)
            _uiState.update { state ->
                state.copy(
                    currentWords = mutableListOf(null, null, null),
                    isGuessWrong = true,
                    shareableWords = uiState.value.titles.shuffled()
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerInteractor.playerDestroy()
    }
}