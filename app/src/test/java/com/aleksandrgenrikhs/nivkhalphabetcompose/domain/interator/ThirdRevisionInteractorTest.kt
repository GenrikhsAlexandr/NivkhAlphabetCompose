package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.createWords
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock

class ThirdRevisionInteractorTest {

    private val repository: AlphabetRepository = mock {
        onBlocking { getWords() } doAnswer { createWords() }
    }
    private val interactor = ThirdRevisionInteractor(repository)

    @Test
    fun `WHEN call getWordsForRevisionThird THEN return list of 3 items`() {
        runTest {
            val actual = interactor.getWordsForRevisionThird()
            val actualSize = actual.size
            assertEquals(3, actualSize)
            assertEquals(actualSize, actual.distinctBy { it.letterId }.size)
        }
    }
}