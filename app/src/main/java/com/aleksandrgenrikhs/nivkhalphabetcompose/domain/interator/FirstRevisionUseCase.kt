package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.FirstRevisionMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueElements
import javax.inject.Inject

class FirstRevisionUseCase
@Inject constructor(
    private val revisionFirstMapper: FirstRevisionMapper,
) {

    fun getLettersForFirstRevision(): List<FirstRevisionModel> {
        val letters = Letters.entries
        val randomLetters = selectUniqueElements(letters, 4) { it.title }
        return revisionFirstMapper.map(randomLetters)
    }
}
