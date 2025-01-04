package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionChooseRightWordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import javax.inject.Inject

class RevisionChooseRightWordMapper
@Inject constructor() : Mapper<List<WordModel>, List<RevisionChooseRightWordModel>> {

    override fun map(input: List<WordModel>): List<RevisionChooseRightWordModel> =
        input.map { word ->
            RevisionChooseRightWordModel(
                title = word.title,
                wordId = word.wordId,
                icon = word.icon
            )
        }
}
