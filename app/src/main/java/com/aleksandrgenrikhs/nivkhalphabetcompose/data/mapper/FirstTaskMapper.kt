package com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
import javax.inject.Inject

class FirstTaskMapper
@Inject constructor() : Mapper<List<WordModel>, List<FirstTaskModel>> {

    override fun map(input: List<WordModel>): List<FirstTaskModel> =
        input.map { word ->
            FirstTaskModel(
                letterId = word.letterId,
                title = word.title,
                wordId = word.wordId,
                icon = word.icon,
            )
        }
}