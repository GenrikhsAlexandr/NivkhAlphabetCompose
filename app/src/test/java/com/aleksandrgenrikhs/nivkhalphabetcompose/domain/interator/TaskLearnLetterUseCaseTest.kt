package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.createWords
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.TaskLearnLetterMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy

class TaskLearnLetterUseCaseTest {

    private val mapper: TaskLearnLetterMapper = spy<TaskLearnLetterMapper>()
    private val repository: AlphabetRepository = mock {
        onBlocking { getWords() } doAnswer { createWords() }
    }
    private val useCase: TaskLearnLetterUseCase = TaskLearnLetterUseCase(
        repository,
        mapper
    )

    @Test
    fun `WHEN call getWordsForFirstTask and letterId is missing THEN throw exception`() = runTest {
        try {
            useCase.getWordsForTaskLearnLetter("unknownId")
            error("Do not reach this")
        } catch (e: Exception) {
            assertEquals("Can't find words for letter unknownId", e.message)
        }
    }

    @Test
    fun `WHEN call getWordsForFirstTask and letterId is known THEN return list words for  this letter`() =
        runTest {
            val actual = useCase.getWordsForTaskLearnLetter("a")
            assertEquals(3, actual.size)
            assert(actual.all { it.letterId == "letterId a" })
            verify(repository).getWords()
            verify(mapper).map(any())
            verifyNoMoreInteractions(repository, mapper)
        }
}
