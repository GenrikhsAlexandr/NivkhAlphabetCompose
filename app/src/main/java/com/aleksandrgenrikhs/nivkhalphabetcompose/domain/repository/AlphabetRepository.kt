package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.FourthTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.ThirdTaskModel
import kotlinx.coroutines.flow.Flow

interface AlphabetRepository {

    suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel>

    suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel>

    suspend fun getWordsForThirdTask(letterId: String): List<ThirdTaskModel>

    suspend fun getWordsForFourthTask(letterId: String): FourthTaskModel

    fun clearPreviousWordsList()

   fun initPlayer(url: String)

    fun play()

    fun isPlaying(): Flow<Boolean>

   fun playerDestroy()
}