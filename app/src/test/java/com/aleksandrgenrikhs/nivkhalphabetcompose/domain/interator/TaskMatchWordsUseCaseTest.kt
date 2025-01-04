package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.createWords
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

class TaskMatchWordsUseCaseTest {

    private val repository: AlphabetRepository = mock {
        onBlocking { getWords() } doAnswer { createWords() }
    }
    private val useCase = TaskMatchWordsUseCase(repository)

    @Test
    fun `WHEN call getWordsForThirdTask and letterId is missing THEN throw exception`() =
        runTest {
            try {
                useCase.getWordsForTaskMatchWords("unknownId")
                error("Do not reach this")
            } catch (e: Exception) {
                assertEquals("Can't find words for letter unknownId", e.message)
            }
        }

    @Test
    fun `WHEN call getWordsForFirstRevisionTask and letterId is known THEN return list of three word with letterId`() =
        runTest {
            val actual = useCase.getWordsForTaskMatchWords("a")
            assertEquals(3, actual.size)
            assertEquals(3, actual.count { it.letterId == "letterId a" })
            verify(repository).getWords()
            verifyNoMoreInteractions(repository)
        }
}
