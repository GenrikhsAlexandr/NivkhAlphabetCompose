package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.TaskFindWordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import javax.inject.Inject

class TaskFindWordMapper
@Inject constructor() : Mapper<List<WordModel>, List<TaskFindWordModel>> {

    override fun map(input: List<WordModel>): List<TaskFindWordModel> =
        input.map { word ->
            TaskFindWordModel(
                letterId = word.letterId,
                title = word.title,
                wordId = word.wordId,
                icon = word.icon,
            )
        }
}
