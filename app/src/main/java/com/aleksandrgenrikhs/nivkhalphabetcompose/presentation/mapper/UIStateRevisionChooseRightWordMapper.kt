package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionChooseRightWordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionChooseRightWordUIState
import javax.inject.Inject
import kotlin.random.Random

class UIStateRevisionChooseRightWordMapper
@Inject constructor() : Mapper<List<RevisionChooseRightWordModel>, RevisionChooseRightWordUIState> {

    override fun map(input: List<RevisionChooseRightWordModel>): RevisionChooseRightWordUIState {
        val list = input.map { it.wordId }
        val index = Random.nextInt(list.size)
        return RevisionChooseRightWordUIState(
            correctWordId = input[index].wordId,
            wordsId = input.map { it.wordId },
            title = input.map { it.title },
            icon = input[index].icon,
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}
