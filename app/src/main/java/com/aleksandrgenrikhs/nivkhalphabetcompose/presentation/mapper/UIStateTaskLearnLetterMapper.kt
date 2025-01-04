package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.TaskLearnLetterModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskLearnLetterUIState
import javax.inject.Inject

class UIStateTaskLearnLetterMapper
@Inject constructor() : Mapper<List<TaskLearnLetterModel>, TaskLearnLetterUIState> {

    override fun map(input: List<TaskLearnLetterModel>): TaskLearnLetterUIState {
        return TaskLearnLetterUIState(
            titles = input.map { it.title },
            wordsId = input.map { it.wordId },
            icons = input.map { it.icon },
            isClickableWords = input.map { it.isClickable },
            progressWords = input.map { it.progress },
            isCompletedWords = input.map { it.isCompleted },
        )
    }
}
