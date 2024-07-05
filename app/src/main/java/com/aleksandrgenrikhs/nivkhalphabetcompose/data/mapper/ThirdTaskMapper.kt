package com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
import javax.inject.Inject

class ThirdTaskMapper
@Inject constructor() : Mapper<List<WordModel>, List<ThirdTaskModel>> {

    override fun map(input: List<WordModel>): List<ThirdTaskModel> =
        input.map { word ->
            ThirdTaskModel(
                title = word.title,
                wordId = word.wordId,
                icon = word.icon,
            )
        }
}