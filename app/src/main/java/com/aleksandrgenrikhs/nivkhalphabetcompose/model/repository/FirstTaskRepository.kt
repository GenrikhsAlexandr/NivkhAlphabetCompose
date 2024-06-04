package com.aleksandrgenrikhs.nivkhalphabet.domain.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.model.Word

interface FirstTaskRepository {

   suspend fun getWords(letterId:String): List <Word>

   fun initPlayer(url: String)

    fun play()

   fun playerDestroy()
}