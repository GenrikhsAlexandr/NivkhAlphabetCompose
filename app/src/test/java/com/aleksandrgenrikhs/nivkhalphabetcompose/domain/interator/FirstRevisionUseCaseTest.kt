package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.FirstRevisionMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions


class FirstRevisionUseCaseTest {

    private val mapper: FirstRevisionMapper = spy(FirstRevisionMapper())
    private val useCase = FirstRevisionUseCase(mapper)

    @Test
    fun `WHEN call getLettersForFirstRevision THEN return list of mapped letters`() {
        val actual = useCase.getLettersForFirstRevision()
        assertEquals(4, actual.size)
        assertEquals(actual.size, actual.distinctBy { it.letter }.size)
        verify(mapper).map(any())
        verifyNoMoreInteractions(mapper)
    }
}