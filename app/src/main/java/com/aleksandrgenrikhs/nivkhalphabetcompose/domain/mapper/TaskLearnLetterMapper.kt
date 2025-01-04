package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.TaskLearnLetterModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import javax.inject.Inject

class TaskLearnLetterMapper
@Inject constructor() : Mapper<List<WordModel>, List<TaskLearnLetterModel>> {

    override fun map(input: List<WordModel>): List<TaskLearnLetterModel> =
        input.map { word ->
            TaskLearnLetterModel(
                letterId = word.letterId,
                title = word.title,
                wordId = word.wordId,
                icon = word.icon,
            )
        }
}
