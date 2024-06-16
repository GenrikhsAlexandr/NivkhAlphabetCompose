package com.aleksandrgenrikhs.nivkhalphabetcompose.model.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.FirstTaskRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.PrefRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlphabetInteractor
@Inject constructor(
    private val firstTaskRepository: FirstTaskRepository,
    private val sharedPreferencesRepository: PrefRepository
) {

    suspend fun getWords(letterId: String): List<WordModel> = firstTaskRepository.getWords(letterId)

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