package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.TaskMatchWordsUIState
import javax.inject.Inject

class UIStateTaskMatchWordsMapper
@Inject constructor() : Mapper<List<WordModel>, TaskMatchWordsUIState> {

    override fun map(input: List<WordModel>): TaskMatchWordsUIState {
        return TaskMatchWordsUIState(
            titles = input.map { it.title },
            wordsId = input.map { it.wordId },
            icons = input.map { it.icon },
            shareableWords = input.shuffled().map { it.title },
        )
    }
}
