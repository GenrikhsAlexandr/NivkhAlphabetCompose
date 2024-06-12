package com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.Word
import kotlinx.coroutines.flow.Flow

interface FirstTaskRepository {

   suspend fun getWords(letterId:String): List <Word>

   fun initPlayer(url: String)

    fun play()

    fun isPlaying(): Flow<Boolean>

   fun playerDestroy()
}