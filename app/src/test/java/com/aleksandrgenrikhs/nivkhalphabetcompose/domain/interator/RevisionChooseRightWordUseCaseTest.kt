package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.createWords
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.RevisionChooseRightWordMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

class RevisionChooseRightWordUseCaseTest {

    private val repository: AlphabetRepository = mock {
        onBlocking { getWords() } doAnswer { createWords() }
    }
    private val mapper: RevisionChooseRightWordMapper = spy(RevisionChooseRightWordMapper())
    private val useCase = RevisionChooseRightWordUseCase(repository, mapper)

    @Test
    fun `WHEN call getWordsForRevisionSecond THEN return list of words`() {
        runTest {
            val actual = useCase.getWordsForRevisionChooseRightWord()
            assertEquals(4, actual.size)
            assertEquals(actual.size, actual.distinctBy { it.wordId }.size)
            verify(mapper).map(any())
            verify(repository).getWords()
            verifyNoMoreInteractions(mapper, repository)
        }
    }
}
