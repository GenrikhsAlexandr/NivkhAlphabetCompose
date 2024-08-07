package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.RevisionFirstMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionFirstModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import javax.inject.Inject

class RevisionFirstInteractor
@Inject constructor(
    private val revisionFirstMapper: RevisionFirstMapper,
) {

    fun getLettersForRevisionFirst(): List<RevisionFirstModel> {
        val letters = Letters.entries
        val randomLetters = selectUniqueRandomElements(letters, 4)
        return revisionFirstMapper.map(randomLetters)
    }
}
