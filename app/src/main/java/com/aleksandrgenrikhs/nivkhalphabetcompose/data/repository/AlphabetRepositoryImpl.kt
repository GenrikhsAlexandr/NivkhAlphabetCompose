package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import androidx.annotation.VisibleForTesting
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.AlphabetDataSource
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.mapper.WordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlphabetRepositoryImpl
@Inject constructor(
    private val wordMapper: WordMapper,
    private val dataSource: AlphabetDataSource
) : AlphabetRepository {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var words: Map<String, List<WordModel>>? = null

    override suspend fun getWords(): Map<String, List<WordModel>> {
        return words ?: withContext(Dispatchers.IO) {
            try {
                words = wordMapper.map(dataSource.getWords())
                words!!
            } catch (e: Exception) {
                emptyMap()
            }
        }
    }
}