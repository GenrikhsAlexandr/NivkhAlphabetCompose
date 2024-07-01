package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.content.Context
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
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_FIRST_TASK
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.NetworkConnected
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
    private val context: Context,
    private val wordMapper: WordMapper,
    private val firstTaskMapper: FirstTaskMapper,
    private val secondTaskMapper: SecondTaskMapper,
    private val mediaPlayer: AlphabetMediaPlayer,
    private val networkConnected: NetworkConnected
) : AlphabetRepository {

    private val json = Json { ignoreUnknownKeys = true }
    private val previousWordsList = mutableSetOf<String>()

    private suspend fun getWords(): List<WordModel> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(WORDS_FIRST_TASK)
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
            filterWordsList.filterNot { it.wordId in previousWordsList }.shuffled().firstOrNull()
        val indexWord1 = Random.nextInt(allWordsList.size)
        var indexWord2 = Random.nextInt(allWordsList.size)
        while (indexWord2 == indexWord1) {
            indexWord2 = Random.nextInt(allWordsList.size)
        }
        val resultList = mutableListOf<WordModel>(
            allWordsList[indexWord1],
            allWordsList[indexWord2]
        )
        correctWord?.let { resultList.add(it) }
        previousWordsList.addAll(resultList.map { it.wordId })
        return secondTaskMapper.map(resultList.shuffled())
    }

    override fun clearPreviousWordsList() {
        previousWordsList.clear()
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
        mediaPlayer.initPlayer(context, url)
    }

    override fun play() {
        mediaPlayer.play()
    }

    override fun isPlaying(): Flow<Boolean> = mediaPlayer.isPlayingPlayer()

    override fun playerDestroy() {
        mediaPlayer.destroyPlayer()
    }

    override fun isNetWorkConnected(): Boolean {
        return networkConnected.isNetworkConnected(context)
    }
}