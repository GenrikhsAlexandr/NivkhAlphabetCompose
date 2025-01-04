package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionListenAndChooseModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionListenAndChooseUIState
import javax.inject.Inject

class UIStateRevisionListenAndChooseMapper
@Inject constructor() : Mapper<List<RevisionListenAndChooseModel>, RevisionListenAndChooseUIState> {

    override fun map(input: List<RevisionListenAndChooseModel>): RevisionListenAndChooseUIState {
        return RevisionListenAndChooseUIState(
            letters = input.map { it.letter },
            correctLetter = input.shuffled().first().letter,
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}
