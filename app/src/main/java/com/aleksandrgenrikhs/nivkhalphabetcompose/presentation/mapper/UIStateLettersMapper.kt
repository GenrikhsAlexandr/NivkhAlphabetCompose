package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.LettersUIState
import javax.inject.Inject

class UIStateLettersMapper
@Inject constructor() : Mapper<List<Letters>, LettersUIState> {

    override fun map(input: List<Letters>): LettersUIState =
        LettersUIState(
            letters = input.map { it.title },
            isLetterCompleted = input.map { it.isCompleted }
        )
}
