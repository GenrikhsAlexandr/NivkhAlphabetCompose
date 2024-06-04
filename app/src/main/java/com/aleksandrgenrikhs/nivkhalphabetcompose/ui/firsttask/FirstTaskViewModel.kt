package com.aleksandrgenrikhs.nivkhalphabetcompose.ui.firsttask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabet.domain.interator.AlphabetInteractor
import com.aleksandrgenrikhs.nivkhalphabet.utils.UrlConstants.ICON_WORD_FIRST_TASK
import com.aleksandrgenrikhs.nivkhalphabet.utils.UrlConstants.LETTER_AUDIO_FIRST_TASK
import com.aleksandrgenrikhs.nivkhalphabet.utils.UrlConstants.WORDS_AUDIO_FIRST_TASK
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val LETTER = 0
const val WORD1 = 1
const val WORD2 = 2
const val WORD3 = 3

@HiltViewModel
class FirstTaskViewModel
@Inject constructor(
    private val interactor: AlphabetInteractor,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FirstTaskUIState> = MutableStateFlow(
        FirstTaskUIState(
            selectedLetter = "",
            isPlaying = false,
            progressLetter = 0,
            progressFirstWord = 0,
            progressSecondWord = 0,
            progressThirdWord = 0,
            isClickableLetter = true,
            isClickableFirstWord = false,
            isClickableSecondWord = false,
            isClickableThirdWord = false,
            isVisibleFirstWord = false,
            isVisibleSecondWord = false,
            isVisibleThirdWord = false,
            wordTitle1 = "",
            wordTitle2 = "",
            wordTitle3 = "",
            wordIcon1 = "",
            wordIcon2 = "",
            wordIcon3 = "",
            navigate = false,
            getWordError = false,
        )
    )
    val uiState: StateFlow<FirstTaskUIState> = _uiState.asStateFlow()

    private val task: Int = Task.FIRST.stableId
    private val words: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())

    fun setLetter(letter: String) {
        _uiState.value = _uiState.value.copy(selectedLetter = letter)
    }

    fun getWords(letterId: String) {
        viewModelScope.launch {
            words.value = interactor.getWords(letterId)
        }
        viewModelScope.launch {
            try {
                words.collect {
                    if (it.isNotEmpty()) {
                        _uiState.value = _uiState.value.copy(
                            getWordError = false,
                            wordTitle1 = it[0].title,
                            wordTitle2 = it[1].title,
                            wordTitle3 = it[2].title
                        )
                        _uiState.value = _uiState.value.copy(
                            wordIcon1 = "${ICON_WORD_FIRST_TASK}${it[0].wordId}.png",
                            wordIcon2 = "${ICON_WORD_FIRST_TASK}${it[1].wordId}.png",
                            wordIcon3 = "${ICON_WORD_FIRST_TASK}${it[2].wordId}.png",
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            getWordError = true
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onClickElement(element: Int) {
        playSound(element)
        viewModelScope.launch {
            _uiState.update { uiState ->
                uiState.copy(
                    isClickableLetter = false,
                    isClickableFirstWord = false,
                    isClickableSecondWord = false,
                    isClickableThirdWord = false,
                )
            }
            when (element) {
                LETTER -> {
                    _uiState.update { uiState ->
                        val newProgressLetter = uiState.progressLetter + 1
                        val newIsVisibleFirstWord = newProgressLetter == 5
                        val isClickableFirstWord = newProgressLetter == 5
                        uiState.copy(
                            progressLetter = newProgressLetter,
                            isVisibleFirstWord = newIsVisibleFirstWord,
                            isClickableFirstWord = isClickableFirstWord
                        )
                    }
                    delayOnClickListener(element, uiState.value.progressLetter)
                }

                WORD1 -> {
                    _uiState.update { uiState ->
                        val newProgressFirstWord = uiState.progressFirstWord + 1
                        val newIsVisibleSecondWord = newProgressFirstWord == 3
                        val isClickableSecondWord = newProgressFirstWord == 3
                        uiState.copy(
                            progressFirstWord = newProgressFirstWord,
                            isVisibleSecondWord = newIsVisibleSecondWord,
                            isClickableSecondWord = isClickableSecondWord
                        )
                    }
                    delayOnClickListener(element, uiState.value.progressFirstWord)
                }

                WORD2 -> {
                    _uiState.update { uiState ->
                        val newProgressSecondWord = uiState.progressSecondWord + 1
                        val newIsVisibleThirdWord = newProgressSecondWord == 3
                        val isClickableThirdWord = newProgressSecondWord == 3

                        uiState.copy(
                            progressSecondWord = newProgressSecondWord,
                            isClickableThirdWord = newIsVisibleThirdWord,
                            isVisibleThirdWord = isClickableThirdWord,
                        )
                    }
                    delayOnClickListener(element, uiState.value.progressSecondWord)
                }

                WORD3 -> {
                    _uiState.update { uiState ->
                        val newProgressThirdWord = uiState.progressThirdWord + 1
                        val navigate = newProgressThirdWord == 3
                        uiState.copy(
                            progressThirdWord = newProgressThirdWord,
                            navigate = navigate
                        )
                    }
                    delayOnClickListener(element, uiState.value.progressThirdWord)
                }
            }
        }
    }

    private fun playSound(element: Int) {
        if (words.value.isNotEmpty()) {
            when (element) {
                LETTER -> {
                    interactor.initPlayer("$LETTER_AUDIO_FIRST_TASK${_uiState.value.selectedLetter.lowercase()}")
                    interactor.play()
                }

                WORD1 -> {
                    val wordId1 = words.value[0].wordId ?: 1.1
                    interactor.initPlayer("$WORDS_AUDIO_FIRST_TASK$wordId1")
                    interactor.play()
                }

                WORD2 -> {
                    val wordId2 = words.value[1].wordId
                    interactor.initPlayer("$WORDS_AUDIO_FIRST_TASK$wordId2")
                    interactor.play()
                }

                WORD3 -> {
                    val wordId3 = words.value[2].wordId
                    interactor.initPlayer("$WORDS_AUDIO_FIRST_TASK$wordId3")
                    interactor.play()
                }

                else -> {}
            }
        }
    }

    private suspend fun delayOnClickListener(element: Int, progress: Int) {
        delay(1500)
        when (element) {
            LETTER ->
                _uiState.update { uiState ->
                    val newIsClickableLetter = progress < 5 && !uiState.isPlaying
                    uiState.copy(
                        isClickableLetter = newIsClickableLetter,
                    )
                }

            WORD1 -> {
                _uiState.update { uiState ->
                    val newIsClickableFirstWord = progress < 3 && !uiState.isPlaying
                    uiState.copy(
                        isClickableFirstWord = newIsClickableFirstWord,
                    )
                }
            }

            WORD2 -> {
                _uiState.update { uiState ->
                    val newIsClickableSecondWord = progress < 3 && !uiState.isPlaying
                    uiState.copy(
                        isClickableSecondWord = newIsClickableSecondWord,
                    )
                }
            }

            WORD3 -> {
                _uiState.update { uiState ->
                    val newIsClickableThirdWord = progress < 3 && !uiState.isPlaying
                    uiState.copy(
                        isClickableThirdWord = newIsClickableThirdWord,
                    )

                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        interactor.playerDestroy()
    }
}