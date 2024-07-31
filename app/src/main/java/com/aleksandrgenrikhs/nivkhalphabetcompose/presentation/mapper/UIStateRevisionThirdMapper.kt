package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionThirdUIState
import javax.inject.Inject

class UIStateRevisionThirdMapper
@Inject constructor() : Mapper<List<WordModel>, RevisionThirdUIState> {

    override fun map(input: List<WordModel>): RevisionThirdUIState {
        val title = input[1].title
        val letter = input[2].letterId
        val icon = input[0].icon

        return RevisionThirdUIState(
            title = title,
            letter = letter,
            icon = icon,
            shareWords = input.filterNot { it.title == title }.shuffled().map { it.title },
            shareLetters = input.filterNot { it.letterId == letter }.shuffled()
                .map { it.letterId },
            shareIcons = input.filterNot { it.icon == icon }.shuffled().map { it.icon },

            correctWords = input.filterNot { it.title == title }.map { it.title },
            correctLetters = input.filterNot { it.letterId == letter }.map { it.letterId },
            correctIcons = input.filterNot { it.icon == icon }.map { it.icon },
        )
    }
}