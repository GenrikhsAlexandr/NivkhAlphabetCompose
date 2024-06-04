package com.aleksandrgenrikhs.nivkhalphabet.domain.interator

import com.aleksandrgenrikhs.nivkhalphabet.domain.repository.FirstTaskRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.Word
import javax.inject.Inject

class AlphabetInteractor
@Inject constructor(
    private val firstTaskRepository: FirstTaskRepository,
) {

    suspend fun getWords(letterId:String): List<Word> = firstTaskRepository.getWords(letterId)

    fun initPlayer(url: String) = firstTaskRepository.initPlayer(url)

    fun play() = firstTaskRepository.play()

    fun playerDestroy() = firstTaskRepository.playerDestroy()

   /* suspend fun taskCompleted(taskId: Int, letterId: String) =
        sharedPreferencesRepository.taskCompleted(taskId, letterId)

    suspend fun getCompletedLetter(taskId: Int) = sharedPreferencesRepository.getCompletedLetter(taskId)
*/
}