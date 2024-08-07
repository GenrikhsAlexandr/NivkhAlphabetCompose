package com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionFirstModel
import javax.inject.Inject

class RevisionFirstMapper
@Inject constructor() : Mapper<List<Letters>, List<RevisionFirstModel>> {

    override fun map(input: List<Letters>): List<RevisionFirstModel> =
        input.map { letter ->
            RevisionFirstModel(
                letter = letter.title,
            )
        }
}