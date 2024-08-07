package com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionSecondModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import javax.inject.Inject

class RevisionSecondMapper
@Inject constructor() : Mapper<List<WordModel>, List<RevisionSecondModel>> {

    override fun map(input: List<WordModel>): List<RevisionSecondModel> =
        input.map { word ->
            RevisionSecondModel(
                title = word.title,
                wordId = word.wordId,
                icon = word.icon
            )
        }
}