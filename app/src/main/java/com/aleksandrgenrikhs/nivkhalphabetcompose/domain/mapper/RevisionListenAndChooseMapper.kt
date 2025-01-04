package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionListenAndChooseModel
import javax.inject.Inject

class RevisionListenAndChooseMapper
@Inject constructor() : Mapper<List<Letters>, List<RevisionListenAndChooseModel>> {

    override fun map(input: List<Letters>): List<RevisionListenAndChooseModel> =
        input.map { letter ->
            RevisionListenAndChooseModel(
                letter = letter.title,
            )
        }
}
