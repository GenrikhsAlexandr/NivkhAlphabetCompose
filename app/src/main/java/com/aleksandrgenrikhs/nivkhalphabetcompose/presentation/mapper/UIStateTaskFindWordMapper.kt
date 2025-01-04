package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.TaskFindWordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskFindWordUIState
import javax.inject.Inject

class UIStateTaskFindWordMapper
@Inject constructor() : Mapper<List<TaskFindWordModel>, TaskFindWordUIState> {

    override fun map(input: List<TaskFindWordModel>): TaskFindWordUIState {
        return TaskFindWordUIState(
            titles = input.map { it.title },
            wordsId = input.map { it.wordId },
            lettersId = input.map { it.letterId },
            icons = input.map { it.icon },
            isFlipped = input.map { it.isFlipped },
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}
