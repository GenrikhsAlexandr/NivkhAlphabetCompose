package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.RevisionSecondMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionSecondModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import javax.inject.Inject

class RevisionSecondInteractor
@Inject constructor(
    private val repository: AlphabetRepository,
    private val revisionSecondMapper: RevisionSecondMapper,
) {

    suspend fun getWordsForRevisionSecond(): List<RevisionSecondModel> {
        val words = repository.getWords()
            .values
            .flatten()
        val selectedWords = selectUniqueRandomElements(words, 4)
        return revisionSecondMapper.map(selectedWords)
    }
}
