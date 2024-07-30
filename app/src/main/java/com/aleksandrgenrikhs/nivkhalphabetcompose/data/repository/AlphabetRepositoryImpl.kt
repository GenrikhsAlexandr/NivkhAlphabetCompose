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
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_URL
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.selectUniqueRandomElements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val mediaPlayer: AlphabetMediaPlayer,
) : AlphabetRepository {

    private val json = Json { ignoreUnknownKeys = true }
    private val previousList = mutableSetOf<String>()

    private suspend fun getWords(): List<WordModel> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(WORDS_URL)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonString = reader.use { it.readText() }
                val response = json.decodeFromString<List<SubjectDto>>(jsonString)
                wordMapper.map(response)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private suspend fun filterWords(letterId: String): List<WordModel> =
        getWords().filter { it.letterId == letterId }

    override suspend fun getWordsForFirstTask(letterId: String): List<FirstTaskModel> {
        return firstTaskMapper.map(filterWords(letterId))
    }

    override suspend fun getWordsForSecondTask(letterId: String): List<SecondTaskModel> {
        val filterWordsList = filterWords(letterId)
        val allWordsList = getWords().filterNot { it in filterWordsList }
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

    override suspend fun getWordsForThirdTask(letterId: String): List<WordModel> =
        filterWords(letterId)

    override suspend fun getWordsForFourthTask(letterId: String): FourthTaskModel {
        val filterWordsList = filterWords(letterId)
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
        val allWords = getWords()
        val selectedWords = selectUniqueRandomElements(allWords, 4)

        return revisionSecondMapper.map(selectedWords)
    }

    override suspend fun getWordsForRevisionThird(): List<WordModel> {
        val allWords = getWords()

        return selectUniqueRandomElements<WordModel>(allWords, 3)
    }

    override fun clearPreviousWordsList() {
        previousList.clear()
    }

    override fun initPlayer(url: String) {
        mediaPlayer.initPlayer(context, url)
    }

    override fun play() {
        mediaPlayer.play()
    }

    override fun isPlaying(): Flow<Boolean> = mediaPlayer.isPlayingPlayer()

    override fun playerDestroy() {
        mediaPlayer.destroyPlayer()
    }
}