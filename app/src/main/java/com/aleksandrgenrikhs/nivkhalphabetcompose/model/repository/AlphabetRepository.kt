package com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.FourthTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
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

    fun isNetWorkConnected(): Boolean
}