package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel

interface AlphabetRepository {

    suspend fun getWords(): Map<String, List<WordModel>>
}