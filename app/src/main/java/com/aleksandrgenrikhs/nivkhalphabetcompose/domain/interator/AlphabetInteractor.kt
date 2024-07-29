package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FourthTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionFirstModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionSecondModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.PrefRepository
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

    suspend fun getWordsForThirdTask(letterId: String): List<ThirdTaskModel> =
        alphabetRepository.getWordsForThirdTask(letterId)

    suspend fun getWordsForFourthTask(letterId: String): FourthTaskModel =
        alphabetRepository.getWordsForFourthTask(letterId)

    suspend fun getLettersForRevisionFirst(): List<RevisionFirstModel> =
        alphabetRepository.getLettersForRevisionFirst()

    suspend fun getWordsForRevisionSecond(): List<RevisionSecondModel> =
        alphabetRepository.getWordsForRevisionSecond()

    fun clearPreviousWordsList() = alphabetRepository.clearPreviousWordsList()

    fun initPlayer(url: String) = alphabetRepository.initPlayer(url)

    fun play() = alphabetRepository.play()

    fun isPlaying(): Flow<Boolean> = alphabetRepository.isPlaying()

    fun playerDestroy() = alphabetRepository.playerDestroy()

    fun taskCompleted(taskId: Int, letterId: String) =
        sharedPreferencesRepository.taskCompleted(taskId, letterId)

    suspend fun isTaskCompleted(taskId: Int, letterId: String): Boolean =
        sharedPreferencesRepository.isTaskCompleted(taskId, letterId)

    suspend fun getLetterCompleted(taskId: Int): List<Letters>? =
        sharedPreferencesRepository.getLetterCompleted(taskId)
}