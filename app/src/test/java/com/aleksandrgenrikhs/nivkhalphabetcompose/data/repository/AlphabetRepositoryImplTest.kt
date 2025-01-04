package com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository

import com.aleksandrgenrikhs.nivkhalphabetcompose.data.AlphabetDataSource
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.SubjectDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.dto.WordDto
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.WordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.WordModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class AlphabetRepositoryImplTest {

    private val wordsDtos: List<SubjectDto> = listOf(
        SubjectDto(
            id = "Aa",
            words = listOf(
                WordDto(
                    title = "Hello",
                    wordId = "1.1"
                )
            )
        ),
        SubjectDto(
            id = "Bb",
            words = listOf(
                WordDto(
                    title = "Nivkh",
                    wordId = "1.2"
                )
            )
        )
    )

    private val words: Map<String, List<WordModel>> = mapOf(
        "Aa" to listOf(
            WordModel(
                letterId = "Aa",
                title = "Hello",
                wordId = "1.1",
                icon = "file:///android_asset/image/1.1.webp"
            )
        ),
        "Bb" to listOf(
            WordModel(
                letterId = "Bb",
                title = "Nivkh",
                wordId = "1.2",
                icon = "file:///android_asset/image/1.2.webp"
            )
        )
    )

    private val wordMapper: WordMapper = spy(WordMapper())
    private val dataSource: AlphabetDataSource =
        mock { onBlocking { getWords() }.thenReturn(wordsDtos) }

    private val repository = AlphabetRepositoryImpl(
        wordMapper = wordMapper,
        dataSource = dataSource,
    )

    @Test
    fun `WHEN getWords and cache is empty THEN get words and save them o cache`() = runTest {
        val result = repository.getWords()
        assertEquals(words, repository.words)
        assertEquals(words, result)
        verify(dataSource).getWords()
        verify(wordMapper).map(wordsDtos)
        verifyNoMoreInteractions(dataSource, wordMapper)
    }

    @Test
    fun `WHEN getWords and cache is not empty THEN get words from cache`() = runTest {
        repository.words = words
        val result = repository.getWords()
        assertEquals(words, result)
        verifyNoInteractions(dataSource, wordMapper)
    }

    @Test
    fun `WHEN getWords is Exception THEN get emptyMap`() = runTest {
        whenever(dataSource.getWords()).thenThrow(RuntimeException::class.java)
        val result = repository.getWords()
        assertEquals(emptyMap<String, List<WordModel>>(), result)
        verify(dataSource).getWords()
        verify(wordMapper, never()).map(wordsDtos)
    }
}
