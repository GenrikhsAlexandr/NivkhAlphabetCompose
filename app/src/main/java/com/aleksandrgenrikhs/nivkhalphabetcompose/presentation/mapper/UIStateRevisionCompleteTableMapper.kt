package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionCompleteTableUIState
import javax.inject.Inject

class UIStateRevisionCompleteTableMapper
@Inject constructor() : Mapper<List<WordModel>, RevisionCompleteTableUIState> {

    override fun map(input: List<WordModel>): RevisionCompleteTableUIState {
        val title = input[1].title
        val letter = input[2].letterId
        val icon = input[0].icon

        return RevisionCompleteTableUIState(
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
