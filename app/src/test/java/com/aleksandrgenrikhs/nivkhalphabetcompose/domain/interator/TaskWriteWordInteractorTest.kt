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

class TaskWriteWordInteractorTest {

    private val repository: AlphabetRepository = mock {
        onBlocking { getWords() } doAnswer { createWords() }
    }
    private val interactor = TaskWriteWordInteractor(repository)

    @Test
    fun `WHEN call getWordsForSecondTask and letterId is missing THEN throw exception`() =
        runTest {
            try {
                interactor.getWordsForTaskWriteWord("unknownId")
                error("Do not reach this")
            } catch (e: Exception) {
                assertEquals("Can't find words for letter unknownId", e.message)
            }
        }

    @Test
    fun `WHEN call getWordsForSecondTask second time with same letterId THEN return list without words witch were used fist run`() =
        runTest {
            val firstCall = interactor.getWordsForTaskWriteWord("a")
            val secondCall = interactor.getWordsForTaskWriteWord("a")
            val thirdCall = interactor.getWordsForTaskWriteWord("a")
            assertNotEquals(firstCall.wordId, secondCall.wordId, thirdCall.wordId)
        }

    @Test
    fun `WHEN clear used words list THEN it should be empty`() = runTest {
        createWords()["a"]?.let { interactor.usedWords.add(it.first()) }
        interactor.clearPreviousWordsList()
        assertTrue(interactor.usedWords.isEmpty())
    }
}
