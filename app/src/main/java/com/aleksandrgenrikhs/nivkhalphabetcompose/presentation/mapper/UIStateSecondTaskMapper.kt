package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondTaskUIState
import javax.inject.Inject

class UIStateSecondTaskMapper
@Inject constructor() : Mapper<List<SecondTaskModel>, SecondTaskUIState> {

    override fun map(input: List<SecondTaskModel>): SecondTaskUIState {
        return SecondTaskUIState(
            title = input.map { it.title },
            wordId = input.map { it.wordId },
            letterId = input.map { it.letterId },
            icon = input.map { it.icon },
            isFlipped = input.map { it.isFlipped },
            isCorrectAnswer = input.map { it.isCorrectAnswer },
            isNetworkConnected = true
        )
    }
}