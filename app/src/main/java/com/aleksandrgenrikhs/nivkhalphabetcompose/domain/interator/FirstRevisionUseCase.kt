package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.FirstRevisionMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import javax.inject.Inject

class FirstRevisionUseCase
@Inject constructor(
    private val revisionFirstMapper: FirstRevisionMapper,
) {

    fun getLettersForFirstRevision(): List<FirstRevisionModel> {
        val letters = Letters.entries
        val randomLetters = selectUniqueRandomElements(letters, 4)
        return revisionFirstMapper.map(randomLetters)
    }
}
