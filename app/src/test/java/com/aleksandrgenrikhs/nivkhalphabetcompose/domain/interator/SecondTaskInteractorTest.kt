package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.createWordModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.createWords
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.SecondTaskMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.DefaultSelectUniqueElements
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.SelectUniqueElements
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock

class SecondTaskInteractorTest {

    private val mapper: SecondTaskMapper = spy(SecondTaskMapper())
    private val repository: AlphabetRepository = mock {
        onBlocking { getWords() } doAnswer { createWords() }
    }
    private val selectUniqueElements: SelectUniqueElements = spy(DefaultSelectUniqueElements())

    private val interactor: SecondTaskInteractor = SecondTaskInteractor(
        mapper,
        repository,
        selectUniqueElements
    )

    @Test
    fun `WHEN call getWordsForSecondTask and letterId is missing THEN throw exception`() =
        runTest {
            try {
                interactor.getWordsForSecondTask("unknownId")
                error("Do not reach this")
            } catch (e: Exception) {
                assertEquals("Can't find words for letter unknownId", e.message)
            }
        }

    @Test
    fun `WHEN call getWordsForSecondTask and letterId is known THEN return list of one correct word and two wrong words`() =
        runTest {
            val actual = interactor.getWordsForSecondTask("a")

            assertEquals(3, actual.size)
            assertEquals(1, actual.count { it.letterId == "letterId a" })
            assertEquals(2, actual.count { it.letterId != "letterId a" })

            verify(repository).getWords()
            verify(mapper).map(any())
            verifyNoMoreInteractions(repository, mapper)
        }

    @Test
    fun `WHEN call getWordsForSecondTask second time with same letterId THEN return list without words witch were used fist run`() =
        runTest {
            val firstCallResult = interactor.getWordsForSecondTask("a")
            val secondCallResult = interactor.getWordsForSecondTask("a")
            firstCallResult.forEach { firstCallWord ->
                assertTrue(secondCallResult.all { firstCallWord.wordId != it.wordId })
            }
        }

    @Test
    fun `WHEN clear used words list THEN it should be empty`() = runTest {
        interactor.usedWords.add(createWordModel("LetterId1", "wordId1"))
        interactor.clearPreviousWordsList()
        assertTrue(interactor.usedWords.isEmpty())
    }
}