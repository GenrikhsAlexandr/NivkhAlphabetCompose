package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstRevisionModel
import javax.inject.Inject

class FirstRevisionMapper
@Inject constructor() : Mapper<List<Letters>, List<FirstRevisionModel>> {

    override fun map(input: List<Letters>): List<FirstRevisionModel> =
        input.map { letter ->
            FirstRevisionModel(
                letter = letter.title,
            )
        }
}