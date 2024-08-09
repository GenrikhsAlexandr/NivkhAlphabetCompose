package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper

import com.aleksandrgenrikhs.nivkhalphabetcompose.Mapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.IMAGE_URL
import javax.inject.Inject

class WordMapper
@Inject constructor() : Mapper<List<SubjectDto>, Map<String, List<WordModel>>> {

    override fun map(input: List<SubjectDto>): Map<String, List<WordModel>> {
        val result = mutableMapOf<String, List<WordModel>>()
        input.forEach() { group ->
            result[group.id] = group.words.map { word ->
                WordModel(
                    letterId = group.id,
                    title = word.title,
                    wordId = word.wordId,
                    icon = "${IMAGE_URL}${word.wordId}.webp"
                )
            }
        }
        return result
    }
}