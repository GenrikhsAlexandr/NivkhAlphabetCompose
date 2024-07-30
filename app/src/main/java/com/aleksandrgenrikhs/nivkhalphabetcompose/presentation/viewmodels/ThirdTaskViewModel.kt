package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.ClipData
import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateThirdTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.ThirdTaskUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ThirdTaskViewModel
@Inject constructor(
    val interactor: AlphabetInteractor,
    val mapper: UIStateThirdTaskMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<ThirdTaskUIState> = MutableStateFlow(ThirdTaskUIState())
    val uiState = _uiState.asStateFlow()

    fun setLetter(letter: String) {
        _uiState.update {
            it.copy(selectedLetter = letter)
        }
    }

    suspend fun getWords(letterId: String) {
        _uiState.update {
            val listWords = mapper.map(interactor.getWordsForThirdTask(letterId))
            with(listWords) {
                it.copy(
                    titles = titles,
                    wordsId = wordsId,
                    icons = icons,
                    shareWords = shareWords,
                )
            }
        }
    }

    fun playSound(url: String) {
        if (url == FINISH_AUDIO) {
            _uiState.update { state ->
                state.copy(
                    isFinishAudio = true
                )
            }
        }
        interactor.playerDestroy()
        interactor.initPlayer(url)
        interactor.play()
    }

    fun updateReceivingContainer(clipData: ClipData?, index: Int) {
        if (clipData == null) return
        val sharedText = clipData.getItemAt(0).text.toString()
        val newShareWords = uiState.value.shareWords.toMutableList()
        val sharedTextIndex = newShareWords.indexOf(sharedText)
        _uiState.update {
            val newCurrentWords = it.currentWords.toMutableList()
            if (sharedTextIndex != -1 && newCurrentWords[index] == null) {
                newShareWords[sharedTextIndex] = null
                newCurrentWords[index] = sharedText
            }
            it.copy(
                currentWords = newCurrentWords,
                shareWords = newShareWords,
                isGuessWrong = false
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
            interactor.taskCompleted(Task.THIRD.stableId, uiState.value.selectedLetter)
        } else {
            playSound(ERROR_AUDIO)
            _uiState.update { state ->
                state.copy(
                    currentWords = mutableListOf(null, null, null),
                    isGuessWrong = true,
                    shareWords = uiState.value.titles.shuffled()
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        interactor.playerDestroy()
    }
}