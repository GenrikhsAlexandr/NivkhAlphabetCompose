package com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.PrefRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlphabetInteractor
@Inject constructor(
    private val alphabetRepository: AlphabetRepository,
    private val sharedPreferencesRepository: PrefRepository
) {

    suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel> =
        alphabetRepository.getWordsForFirstTask(letterId)

    suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel> =
        alphabetRepository.getWordsForSecondTask(letterId)

    fun clearPreviousWordsList() = alphabetRepository.clearPreviousWordsList()

    suspend fun getShuffledWord(letterId: String): ThirdTaskModel =
        alphabetRepository.shuffledWord(letterId)

    fun initPlayer(url: String) = alphabetRepository.initPlayer(url)

    fun play() = alphabetRepository.play()

    fun isPlaying(): Flow<Boolean> = alphabetRepository.isPlaying()

    fun playerDestroy() = alphabetRepository.playerDestroy()

    fun taskCompleted(taskId: Int, letterId: String) =
        sharedPreferencesRepository.taskCompleted(taskId, letterId)

    fun isTaskCompleted(taskId: Int, letterId: String): Boolean =
        sharedPreferencesRepository.isTaskCompleted(taskId, letterId)

    fun getLetterCompleted(letters: Int): List<Letters>? =
        sharedPreferencesRepository.getLetterCompleted(letters)
}