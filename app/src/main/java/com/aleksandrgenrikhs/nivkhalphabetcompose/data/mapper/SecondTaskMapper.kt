package com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
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