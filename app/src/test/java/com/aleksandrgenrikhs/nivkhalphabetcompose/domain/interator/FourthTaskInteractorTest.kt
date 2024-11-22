package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.createWords
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock

class FourthTaskInteractorTest {

    private val repository: AlphabetRepository = mock {
        onBlocking { getWords() } doAnswer { createWords() }
    }
    private val interactor = FourthTaskInteractor(repository)

    @Test
    fun `WHEN call getWordsForSecondTask and letterId is missing THEN throw exception`() =
        runTest {
            try {
                interactor.getWordsForFourthTask("unknownId")
                error("Do not reach this")
            } catch (e: Exception) {
                assertEquals("Can't find words for letter unknownId", e.message)
            }
        }

    @Test
    fun `WHEN call getWordsForSecondTask second time with same letterId THEN return list without words witch were used fist run`() =
        runTest {
            val firstCall = interactor.getWordsForFourthTask("a")
            val secondCall = interactor.getWordsForFourthTask("a")
            val thirdCall = interactor.getWordsForFourthTask("a")
            assertNotEquals(firstCall.wordId, secondCall.wordId, thirdCall.wordId)
        }

    @Test
    fun `WHEN clear used words list THEN it should be empty`() = runTest {
        createWords()["a"]?.let { interactor.usedWords.add(it.first()) }
        interactor.clearPreviousWordsList()
        assertTrue(interactor.usedWords.isEmpty())
    }
}