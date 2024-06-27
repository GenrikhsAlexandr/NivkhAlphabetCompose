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
    private val firstTaskRepository: AlphabetRepository,
    private val sharedPreferencesRepository: PrefRepository
) {

    suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel> =
        firstTaskRepository.getWordsForFirstTask(letterId)

    suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel> =
        firstTaskRepository.getWordsForSecondTask(letterId)

    suspend fun getShuffledWord(letterId: String): ThirdTaskModel =
        firstTaskRepository.shuffledWord(letterId)

    fun initPlayer(url: String) = firstTaskRepository.initPlayer(url)

    fun play() = firstTaskRepository.play()

    fun isPlaying(): Flow<Boolean> = firstTaskRepository.isPlaying()

    fun playerDestroy() = firstTaskRepository.playerDestroy()

    fun taskCompleted(taskId: Int, letterId: String) =
        sharedPreferencesRepository.taskCompleted(taskId, letterId)

    fun isTaskCompleted(taskId: Int, letterId: String): Boolean =
        sharedPreferencesRepository.isTaskCompleted(taskId, letterId)

    fun getLetterCompleted(letters: Int): List<Letters>? =
        sharedPreferencesRepository.getLetterCompleted(letters)
}