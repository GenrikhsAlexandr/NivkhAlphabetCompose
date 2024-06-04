package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.app.Application
import com.aleksandrgenrikhs.nivkhalphabet.domain.repository.FirstTaskRepository
import com.aleksandrgenrikhs.nivkhalphabet.utils.AlphabetMediaPlayer
import com.aleksandrgenrikhs.nivkhalphabet.utils.UrlConstants.WORDS_FIRST_TASK
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.firsttask.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.firsttask.mapper.FirstTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class FirstTaskRepositoryImpl
@Inject constructor(
    private val application: Application,
    private val mapper: FirstTaskMapper,
    private val mediaPlayer: AlphabetMediaPlayer
) : FirstTaskRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getWords(letterId: String): List<Word> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = application.assets.open(WORDS_FIRST_TASK)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonString = reader.use { it.readText() }
                val response = json.decodeFromString<List<SubjectDto>>(jsonString)
                val words = mapper.map(response)
                words.filter {
                    it.letterId == letterId
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override fun initPlayer(url: String) {
        mediaPlayer.initPlayer(application, url)
    }

    override fun play() {
        mediaPlayer.play()
    }

    override fun playerDestroy() {
        mediaPlayer.destroyPlayer()
    }
}