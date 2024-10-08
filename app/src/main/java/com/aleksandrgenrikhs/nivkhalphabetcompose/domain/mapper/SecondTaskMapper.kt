package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import javax.inject.Inject

class SecondTaskMapper
@Inject constructor() : Mapper<List<WordModel>, List<SecondTaskModel>> {

    override fun map(input: List<WordModel>): List<SecondTaskModel> =
        input.map { word ->
            SecondTaskModel(
                letterId = word.letterId,
                title = word.title,
                wordId = word.wordId,
                icon = word.icon,
            )
        }
}