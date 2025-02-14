package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.ClipData
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.RevisionCompleteTableInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateRevisionCompleteTableMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionCompleteTableUIState
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
class RevisionCompleteTableViewModel
@Inject constructor(
    private val completeTableUseCase: RevisionCompleteTableInteractor,
    private val uiStateMapper: UIStateRevisionCompleteTableMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionCompleteTableUIState> =
        MutableStateFlow(RevisionCompleteTableUIState())
    val uiState = _uiState.asStateFlow()

    private var wordsUIState: RevisionCompleteTableUIState? = null

    suspend fun updateWords() {
        _uiState.update { state ->
            val words = completeTableUseCase.getWordsForRevisionCompleteTable()
            wordsUIState = uiStateMapper.map(words)
            wordsUIState?.let { mappedWords ->
                with(mappedWords) {
                    state.copy(
                        title = title,
                        letter = letter,
                        icon = icon,
                        shareWords = shareWords,
                        shareLetters = shareLetters,
                        shareIcons = shareIcons,
                        correctWords = correctWords,
                        correctIcons = correctIcons,
                        correctLetters = correctLetters,
                    )
                }
            } ?: state
        }
    }

    fun updateReceivingContainer(clipData: ClipData?, index: Int) {
        if (clipData == null) return
        val sharedText = clipData.getItemAt(0).text.toString()
        val newShareWords = uiState.value.shareWords.toMutableList()
        val newShareLetters = uiState.value.shareLetters.toMutableList()
        val newShareIcons = uiState.value.shareIcons.toMutableList()

        val sharedTextIndexWords = newShareWords.indexOf(sharedText)
        val sharedTextIndexLetters = newShareLetters.indexOf(sharedText)
        val sharedTextIndexIcons = newShareIcons.indexOf(sharedText)

        _uiState.update {
            val newCurrentWords = it.currentWords.toMutableList()
            val newCurrentLetters = it.currentLetters.toMutableList()
            val newCurrentIcons = it.currentIcons.toMutableList()

            if (sharedTextIndexWords != -1 && newCurrentWords[index] == null) {
                newShareWords[sharedTextIndexWords] = null
                newCurrentWords[index] = sharedText
            }

            if (sharedTextIndexLetters != -1 && newCurrentLetters[index] == null) {
                newShareLetters[sharedTextIndexLetters] = null
                newCurrentLetters[index] = sharedText
            }

            if (sharedTextIndexIcons != -1 && newCurrentIcons[index] == null) {
                newShareIcons[sharedTextIndexIcons] = null
                newCurrentIcons[index] = sharedText
            }

            it.copy(
                currentWords = newCurrentWords,
                currentLetters = newCurrentLetters,
                currentIcons = newCurrentIcons,
                shareWords = newShareWords,
                shareLetters = newShareLetters,
                shareIcons = newShareIcons,
                isGuessWrong = false
            )
        }
    }

    fun checkAnswer() {
        val currentWords = uiState.value.currentWords
        val currentLetters = uiState.value.currentLetters
        val currentIcons = uiState.value.currentIcons
        val correctWords = uiState.value.correctWords
        val correctIcons = uiState.value.correctIcons
        val correctLetters = uiState.value.correctLetters
        if (currentWords == correctWords && currentLetters == correctLetters && currentIcons == correctIcons) {
            showDialog()
        } else {
            playSound(ERROR_AUDIO)
            _uiState.update { state ->
                wordsUIState?.let { words ->
                    state.copy(
                        currentWords = mutableListOf(null, null),
                        currentLetters = mutableListOf(null, null),
                        currentIcons = mutableListOf(null, null),
                        isGuessWrong = true,
                        shareIcons = words.shareIcons,
                        shareWords = words.shareWords,
                        shareLetters = words.shareLetters,
                    )
                } ?: state
            }
        }
    }

    fun resetState() {
        _uiState.update { state ->
            wordsUIState?.let { words ->
                state.copy(
                    currentWords = mutableListOf(null, null),
                    currentLetters = mutableListOf(null, null),
                    currentIcons = mutableListOf(null, null),
                    isGuessWrong = false,
                    shareIcons = words.shareIcons,
                    shareWords = words.shareWords,
                    shareLetters = words.shareLetters,
                    resetTrigger = !state.resetTrigger
                )
            } ?: state
        }
    }

    private fun showDialog() {
        viewModelScope.launch {
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
