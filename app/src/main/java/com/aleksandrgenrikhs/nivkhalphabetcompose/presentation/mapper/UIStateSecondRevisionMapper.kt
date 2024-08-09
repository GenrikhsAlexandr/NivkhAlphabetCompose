package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SecondRevisionUIState
import javax.inject.Inject
import kotlin.random.Random

class UIStateSecondRevisionMapper
@Inject constructor() : Mapper<List<SecondRevisionModel>, SecondRevisionUIState> {

    override fun map(input: List<SecondRevisionModel>): SecondRevisionUIState {
        val list = input.map { it.wordId }
        val index = Random.nextInt(list.size)
        return SecondRevisionUIState(
            correctWordId = input[index].wordId,
            wordsId = input.map { it.wordId },
            title = input.map { it.title },
            icon = input[index].icon,
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}