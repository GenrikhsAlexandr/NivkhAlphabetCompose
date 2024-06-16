package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import android.app.Application
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.FirstTaskRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import com.aleksandrgenrikhs.nivkhalphabet.utils.UrlConstants.WORDS_FIRST_TASK
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.WordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.WordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class FirstTaskRepositoryImpl
@Inject constructor(
	private val application: Application,
	private val mapper: WordMapper,
	private val mediaPlayer: AlphabetMediaPlayer
) : FirstTaskRepository {

	private val json = Json { ignoreUnknownKeys = true }

	override suspend fun getWords(letterId: String): List<WordModel> {
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

	override fun isPlaying(): Flow<Boolean> = mediaPlayer.isPlayingPlayer()

	override fun playerDestroy() {
		mediaPlayer.destroyPlayer()
	}
}