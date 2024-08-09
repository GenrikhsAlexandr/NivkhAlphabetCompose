package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.FirstRevisionUIState
import javax.inject.Inject

class UIStateFirstRevisionMapper
@Inject constructor() : Mapper<List<FirstRevisionModel>, FirstRevisionUIState> {

    override fun map(input: List<FirstRevisionModel>): FirstRevisionUIState {
        return FirstRevisionUIState(
            letters = input.map { it.letter },
            correctLetter = input.shuffled().first().letter,
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}