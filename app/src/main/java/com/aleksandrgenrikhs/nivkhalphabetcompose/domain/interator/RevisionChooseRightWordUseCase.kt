package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.RevisionChooseRightWordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionChooseRightWordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueElements
import javax.inject.Inject

class RevisionChooseRightWordUseCase
@Inject constructor(
    private val repository: AlphabetRepository,
    private val chooseRightWordMapper: RevisionChooseRightWordMapper,
) {

    suspend fun getWordsForRevisionChooseRightWord(): List<RevisionChooseRightWordModel> {
        val words = repository.getWords()
            .values
            .flatten()
        val selectedWords = selectUniqueElements(words, 4) { it.letterId }
        return chooseRightWordMapper.map(selectedWords)
    }
}
