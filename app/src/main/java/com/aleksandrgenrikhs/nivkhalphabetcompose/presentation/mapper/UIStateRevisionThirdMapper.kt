package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionThirdUIState
import javax.inject.Inject

class UIStateRevisionThirdMapper
@Inject constructor() : Mapper<List<WordModel>, RevisionThirdUIState> {

    override fun map(input: List<WordModel>): RevisionThirdUIState {
        return RevisionThirdUIState(
            titles = input.map { it.title },
            letters = input.map { it.letterId },
            icons = input.map { it.icon },
            shareIcons = input.filterIndexed { index, _ -> index != 0 }.map { it.icon },
            shareWords = input.filterIndexed { index, _ -> index != 1 }.shuffled().map { it.title },
            shareLetters = input.filterIndexed { index, _ -> index != 2 }.shuffled()
                .map { it.letterId },
        )
    }
}