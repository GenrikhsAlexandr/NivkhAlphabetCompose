package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondTaskUIState
import javax.inject.Inject

class UIStateSecondTaskMapper
@Inject constructor() : Mapper<List<SecondTaskModel>, SecondTaskUIState> {

    override fun map(input: List<SecondTaskModel>): SecondTaskUIState {
        return SecondTaskUIState(
            titles = input.map { it.title },
            wordsId = input.map { it.wordId },
            lettersId = input.map { it.letterId },
            icons = input.map { it.icon },
            isFlipped = input.map { it.isFlipped },
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}