package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.content.Context
import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.FirstTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.RevisionFirstMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.RevisionSecondMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.SecondTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.WordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FourthTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionFirstModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.RevisionSecondModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_URL
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class AlphabetRepositoryImpl
@Inject constructor(
    private val context: Context,
    private val wordMapper: WordMapper,
    private val firstTaskMapper: FirstTaskMapper,
    private val secondTaskMapper: SecondTaskMapper,
    private val revisionFirstMapper: RevisionFirstMapper,
    private val revisionSecondMapper: RevisionSecondMapper,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : AlphabetRepository {

    private val previousList = mutableSetOf<String>()
    private var words: Map<String, List<WordModel>>? = null

    override suspend fun getWords(): Map<String, List<WordModel>> {
        if (words != null) {
            return words!!
        }
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(WORDS_URL)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonString = reader.use { it.readText() }
                val response = json.decodeFromString<List<SubjectDto>>(jsonString)
                words = wordMapper.map(response)
                words!!
            } catch (e: Exception) {
                emptyMap()
            }
        }
    }

    override suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel> {
        val filterWordsList = getWords()[letterId] ?: emptyList()
        return firstTaskMapper.map(filterWordsList)
    }

    override suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel> {
        val filterWordsList = getWords()[letterId] ?: emptyList()
        val allWordsList = getWords().values.flatten().filterNot { it in filterWordsList }
        val correctWord =
            filterWordsList.filterNot { it.wordId in previousList }.shuffled().firstOrNull()
        val selectedWords = selectUniqueRandomElements(allWordsList, 2)
        val resultList = mutableListOf<WordModel>().apply {
            addAll(selectedWords)
            correctWord?.let {
                add(it)
            }
        }
        previousList.addAll(resultList.map { it.wordId })
        return secondTaskMapper.map(resultList.shuffled())
    }

    override suspend fun getWordsForThirdTask(letterId: String): List<WordModel> {
        val filterWordsList = getWords()[letterId] ?: emptyList()
        return filterWordsList
    }

    override suspend fun getWordsForFourthTask(letterId: String): FourthTaskModel {
        val filterWordsList = words?.get(letterId) ?: emptyList()
        val currentWord =
            filterWordsList.filterNot { it.wordId in previousList }.shuffled().firstOrNull()
        currentWord?.let { previousList.add(it.wordId) }
        return FourthTaskModel(
            title = currentWord?.title ?: "",
            icon = currentWord?.icon ?: "",
            wordId = currentWord?.wordId ?: ""
        )
    }

    override suspend fun getLettersForRevisionFirst(): List<RevisionFirstModel> {
        val allLetters = Letters.entries
        val selectedLetters = selectUniqueRandomElements(allLetters, 4)
        return revisionFirstMapper.map(selectedLetters)
    }

    override suspend fun getWordsForRevisionSecond(): List<RevisionSecondModel> {
        val allWords = getWords().values.flatten()
        val selectedWords = selectUniqueRandomElements(allWords, 4)
        return revisionSecondMapper.map(selectedWords)
    }

    override suspend fun getWordsForRevisionThird(): List<WordModel> {
        val allWords = getWords().values.flatten()
        return selectUniqueRandomElements<WordModel>(allWords, 3)
    }

    override fun clearPreviousWordsList() {
        previousList.clear()
    }
}