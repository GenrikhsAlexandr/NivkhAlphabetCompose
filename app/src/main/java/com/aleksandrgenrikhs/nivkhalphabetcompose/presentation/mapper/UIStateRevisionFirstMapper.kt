package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionFirstModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionFirstUIState
import javax.inject.Inject

class UIStateRevisionFirstMapper
@Inject constructor() : Mapper<List<RevisionFirstModel>, RevisionFirstUIState> {

    override fun map(input: List<RevisionFirstModel>): RevisionFirstUIState {
        return RevisionFirstUIState(
            letters = input.map { it.letter },
            correctLetter = input.shuffled().first().letter,
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}