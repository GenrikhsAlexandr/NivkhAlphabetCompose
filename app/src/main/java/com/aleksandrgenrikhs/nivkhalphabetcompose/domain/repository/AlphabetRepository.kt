package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FourthTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionFirstModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionSecondModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.ThirdTaskModel
import kotlinx.coroutines.flow.Flow

interface AlphabetRepository {

    suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel>

    suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel>

    suspend fun getWordsForThirdTask(letterId: String): List<ThirdTaskModel>

    suspend fun getWordsForFourthTask(letterId: String): FourthTaskModel

    suspend fun getLettersForRevisionFirst(): List<RevisionFirstModel>

    suspend fun getWordsForRevisionSecond(): List<RevisionSecondModel>


    fun clearPreviousWordsList()

   fun initPlayer(url: String)

    fun play()

    fun isPlaying(): Flow<Boolean>

   fun playerDestroy()
}