package com.aleksandrgenrikhs.nivkhalphabetcompose.data.firsttask.mapper

import com.aleksandrgenrikhs.nivkhalphabet.utils.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.firsttask.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.Word
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.ICON_WORD_FIRST_TASK
import javax.inject.Inject

class FirstTaskMapper
@Inject constructor() : Mapper<List<SubjectDto>, List<Word>> {

    override fun map(input: List<SubjectDto>): List<Word> =
        input.flatMap { words ->
            words.words.map {
                Word(
                    letterId = words.id,
                    title = it.title,
                    wordId = it.wordId,
                    icon = "${ICON_WORD_FIRST_TASK}${it.wordId}.png"
                )
            }
        }
}