package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.FirstTaskUIState
import javax.inject.Inject

class UIStateFirstTaskMapper
@Inject constructor() : Mapper<List<FirstTaskModel>, FirstTaskUIState> {

    override fun map(input: List<FirstTaskModel>): FirstTaskUIState {
        return FirstTaskUIState(
            title = input.map { it.title },
            wordId = input.map { it.wordId },
            icon = input.map { it.icon },
            isClickableWord = input.map { it.isClickable },
            progressWord = input.map { it.progress },
            isCompletedWord = input.map { it.isCompleted },
        )
    }
}