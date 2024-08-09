package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.FirstTaskUIState
import javax.inject.Inject

class UIStateFirstTaskMapper
@Inject constructor() : Mapper<List<FirstTaskModel>, FirstTaskUIState> {

    override fun map(input: List<FirstTaskModel>): FirstTaskUIState {
        return FirstTaskUIState(
            titles = input.map { it.title },
            wordsId = input.map { it.wordId },
            icons = input.map { it.icon },
            isClickableWords = input.map { it.isClickable },
            progressWords = input.map { it.progress },
            isCompletedWords = input.map { it.isCompleted },
        )
    }
}