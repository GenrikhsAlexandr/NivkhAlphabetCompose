package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.app.Application
import com.aleksandrgenrikhs.nivkhalphabet.utils.UrlConstants.WORDS_FIRST_TASK
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.FirstTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.SecondTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.WordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.FirstTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.SecondTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.ThirdTaskModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import kotlin.random.Random

class AlphabetRepositoryImpl
@Inject constructor(
    private val application: Application,
    private val wordMapper: WordMapper,
    private val firstTaskMapper: FirstTaskMapper,
    private val secondTaskMapper: SecondTaskMapper,
    private val mediaPlayer: AlphabetMediaPlayer
) : AlphabetRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getWords(): List<WordModel> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = application.assets.open(WORDS_FIRST_TASK)
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
        val randomFilterWord = filterWordsList.firstOrNull()
        val randomWordsFromAll = allWordsList.shuffled().take(2)
        val resultList = mutableListOf<WordModel>()
        randomFilterWord?.let { resultList.add(it) }
        resultList.addAll(randomWordsFromAll)
        return secondTaskMapper.map(resultList.shuffled())
    }

    override suspend fun shuffledWord(letterId: String): ThirdTaskModel {
        val listWords = filterWords(letterId)
        val index = Random.nextInt(listWords.size)
        val iconWord = listWords[index].icon
        val shuffledWord = listWords[index].title.toCharArray().toList().shuffled()
        return ThirdTaskModel(
            title = shuffledWord,
            icon = iconWord,
        )
    }

    override fun initPlayer(url: String) {
        mediaPlayer.initPlayer(application, url)
    }

    override fun play() {
        mediaPlayer.play()
    }

    override fun isPlaying(): Flow<Boolean> = mediaPlayer.isPlayingPlayer()

    override fun playerDestroy() {
        mediaPlayer.destroyPlayer()
    }
}