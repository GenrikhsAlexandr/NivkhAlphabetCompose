package com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ICON_WORD_FIRST_TASK
import javax.inject.Inject

class WordMapper
@Inject constructor() : Mapper<List<SubjectDto>, List<WordModel>> {

    override fun map(input: List<SubjectDto>): List<WordModel> =
        input.flatMap { words ->
            words.words.map {
                WordModel(
                    letterId = words.id,
                    title = it.title,
                    wordId = it.wordId,
                    icon = "${ICON_WORD_FIRST_TASK}${it.wordId}.png"
                )
            }
        }
}