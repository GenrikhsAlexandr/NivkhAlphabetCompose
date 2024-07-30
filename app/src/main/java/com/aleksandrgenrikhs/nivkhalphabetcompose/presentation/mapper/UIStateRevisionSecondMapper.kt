package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionSecondModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.RevisionSecondUIState
import javax.inject.Inject
import kotlin.random.Random

class UIStateRevisionSecondMapper
@Inject constructor() : Mapper<List<RevisionSecondModel>, RevisionSecondUIState> {

    override fun map(input: List<RevisionSecondModel>): RevisionSecondUIState {
        val list = input.map { it.wordId }
        val index = Random.nextInt(list.size)
        return RevisionSecondUIState(
            correctWordId = input[index].wordId,
            wordsId = input.map { it.wordId },
            words = input.map { it.title },
            icon = input[index].icon,
            isCorrectAnswers = input.map { it.isCorrectAnswer },
        )
    }
}