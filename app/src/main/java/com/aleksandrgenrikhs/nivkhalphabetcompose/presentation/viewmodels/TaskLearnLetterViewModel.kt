package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.Task
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.MediaPlayerInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.TaskLearnLetterUseCase
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper.UIStateTaskLearnLetterMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskLearnLetterUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FINISH_AUDIO
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.LETTER_AUDIO
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
class TaskLearnLetterViewModel
@Inject constructor(
    private val prefInteractor: PrefInteractor,
    private val learnLetterUseCase: TaskLearnLetterUseCase,
    private val uiStateMapper: UIStateTaskLearnLetterMapper,
    private val mediaPlayerInteractor: MediaPlayerInteractor,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskLearnLetterUIState> =
        MutableStateFlow(TaskLearnLetterUIState())
    val uiState = _uiState.asStateFlow()

    fun setSelectedLetter(letter: String) {
        _uiState.update {
            _uiState.value.copy(
                selectedLetter = letter,
            )
        }
    }

    init {
        viewModelScope.launch {
            prefInteractor.saveStartTimeLearning()
        }
    }

    suspend fun updateWordsForLetter(letterId: String) {
        val letteredWords = learnLetterUseCase.getWordsForTaskLearnLetter(letterId)
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
            val isTaskCompleted = prefInteractor.isTaskCompleted(
                Task.LEARN_LETTER.stableId,
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
                    !it && uiState.value.isCompletedLetter ||
                            !it && uiState.value.isCompletedWords[0] ||
                            !it && uiState.value.isCompletedWords[1] ||
                            !it && uiState.value.isCompletedWords[2] -> mediaPlayerInteractor.playerDestroy()
                }
            }
        }
    }

    fun onClickLetter(letter: String) {
        playSound("$LETTER_AUDIO$letter")
        updatePlayingState()
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

    fun onClickWord(word: String, index: Int?) {
        playSound("$WORDS_AUDIO$word")
        updatePlayingState()
        if (index == null) return
        _uiState.update { state ->
            val (newProgressWord, newWordIsCompleted, newIsClickable) = updateWordState(
                index,
                uiState.value.progressWords,
                uiState.value.isCompletedWords,
                uiState.value.isClickableWords
            )
            state.copy(
                progressWords = newProgressWord,
                isCompletedWords = newWordIsCompleted,
                isClickableWords = newIsClickable,
            )
        }
        val isAllWordsCompleted = uiState.value.isCompletedWords.isNotEmpty() &&
                uiState.value.isCompletedWords.last()
        if (isAllWordsCompleted) {
            _uiState.update { state ->
                state.copy(
                    isClickableWords = listOf(false, false, false),
                )
            }
            showDialog()
            saveTaskProgress()
        }
    }

    private fun updateWordState(
        index: Int,
        progressWords: List<Int>,
        isCompletedWords: List<Boolean>,
        isClickableWords: List<Boolean>,
    ): Triple<List<Int>, List<Boolean>, List<Boolean>> {
        val newProgressWord = progressWords.toMutableList()
        val newWordIsCompleted = isCompletedWords.toMutableList()
        val newIsClickable = isClickableWords.toMutableList()

        newProgressWord[index] = progressWords[index] + 1
        newWordIsCompleted[index] = newProgressWord[index] > 2

        if (newWordIsCompleted[index] && index < isCompletedWords.lastIndex) {
            newIsClickable[index + 1] = true
        }

        return Triple(newProgressWord, newWordIsCompleted, newIsClickable)
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

    private fun saveTaskProgress() {
        prefInteractor.taskCompleted(
            Task.LEARN_LETTER.stableId,
            uiState.value.selectedLetter
        )
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
