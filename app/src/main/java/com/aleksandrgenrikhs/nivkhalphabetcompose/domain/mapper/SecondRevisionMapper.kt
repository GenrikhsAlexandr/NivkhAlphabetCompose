package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import javax.inject.Inject

class SecondRevisionMapper
@Inject constructor() : Mapper<List<WordModel>, List<SecondRevisionModel>> {

    override fun map(input: List<WordModel>): List<SecondRevisionModel> =
        input.map { word ->
            SecondRevisionModel(
                title = word.title,
                wordId = word.wordId,
                icon = word.icon
            )
        }
}