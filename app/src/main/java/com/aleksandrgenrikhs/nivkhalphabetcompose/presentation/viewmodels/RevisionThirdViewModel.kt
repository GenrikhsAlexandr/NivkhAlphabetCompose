package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.ClipData
import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateRevisionThirdMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionThirdUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ERROR_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RevisionThirdViewModel
@Inject constructor(
    val interactor: AlphabetInteractor,
    val mapper: UIStateRevisionThirdMapper
) : ViewModel() {

    private val _uiState: MutableStateFlow<RevisionThirdUIState> =
        MutableStateFlow(RevisionThirdUIState())
    val uiState = _uiState.asStateFlow()

    private var listWords: RevisionThirdUIState? = null
    suspend fun getWords() {
        _uiState.update {
            listWords = mapper.map(interactor.getWordsForRevisionThird())
            with(listWords!!) {
                it.copy(
                    titles = titles,
                    letters = letters,
                    icons = icons,
                    shareWords = shareWords,
                    shareLetters = shareLetters,
                    shareIcons = shareIcons,
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
        val correctWords = uiState.value.titles
        if (currentWords == correctWords) {
            _uiState.update { state ->
                state.copy(
                    isAnswerCorrect = true
                )
            }
        } else {
            playSound(ERROR_AUDIO)
            _uiState.update { state ->
                state.copy(
                    currentWords = mutableListOf(null, null, null),
                    currentLetters = mutableListOf(null, null, null),
                    currentIcons = mutableListOf(null, null, null),
                    isGuessWrong = true,
                    shareIcons = listWords!!.shareIcons,
                    shareWords = listWords!!.shareWords,
                    shareLetters = listWords!!.shareLetters,
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        interactor.playerDestroy()
    }
}