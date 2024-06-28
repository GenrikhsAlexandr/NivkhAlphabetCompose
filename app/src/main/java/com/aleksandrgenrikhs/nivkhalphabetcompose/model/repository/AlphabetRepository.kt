package com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
import kotlinx.coroutines.flow.Flow

interface AlphabetRepository {

    suspend fun getWords(): List<WordModel>
    suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel>

    suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel>

    fun clearPreviousWordsList()

    suspend fun shuffledWord(letterId: String): ThirdTaskModel

   fun initPlayer(url: String)

    fun play()

    fun isPlaying(): Flow<Boolean>

   fun playerDestroy()
}