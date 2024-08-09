package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
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