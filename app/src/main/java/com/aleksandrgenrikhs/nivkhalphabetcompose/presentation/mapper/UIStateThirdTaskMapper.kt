package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.ThirdTaskUIState
import javax.inject.Inject

class UIStateThirdTaskMapper
@Inject constructor() : Mapper<List<WordModel>, ThirdTaskUIState> {

    override fun map(input: List<WordModel>): ThirdTaskUIState {
        return ThirdTaskUIState(
            titles = input.map { it.title },
            wordsId = input.map { it.wordId },
            icons = input.map { it.icon },
            shareWords = input.shuffled().map { it.title },
        )
    }
}