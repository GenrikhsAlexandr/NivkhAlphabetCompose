package com.aleksandrgenrikhs.nivkhalphabetcompose.data

import android.content.Context
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.WORDS_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class AlphabetDataSource
@Inject constructor(
    private val context: Context,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {

    suspend fun getWords(): List<SubjectDto> {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = context.assets.open(WORDS_URL)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonString = reader.use { it.readText() }
                val response = json.decodeFromString<List<SubjectDto>>(jsonString)
                response
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}