package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.ThirdTaskUIState
import javax.inject.Inject

class UIStateThirdTaskMapper
@Inject constructor() : Mapper<List<ThirdTaskModel>, ThirdTaskUIState> {

    override fun map(input: List<ThirdTaskModel>): ThirdTaskUIState {
        return ThirdTaskUIState(
            title = input.map { it.title },
            wordId = input.map { it.wordId },
            icon = input.map { it.icon },
            shareWords = input.shuffled().map { it.title },
            isNetworkConnected = true
        )
    }
}