package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.RevisionListenAndChooseMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionListenAndChooseModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueElements
import javax.inject.Inject

class RevisionListenAndChooseUseCase
@Inject constructor(
    private val listenAndChooseMapper: RevisionListenAndChooseMapper,
) {

    fun getLettersForRevisionListenAndChoose(): List<RevisionListenAndChooseModel> {
        val letters = Letters.entries
        val randomLetters = selectUniqueElements(letters, 4) { it.title }
        return listenAndChooseMapper.map(randomLetters)
    }
}
