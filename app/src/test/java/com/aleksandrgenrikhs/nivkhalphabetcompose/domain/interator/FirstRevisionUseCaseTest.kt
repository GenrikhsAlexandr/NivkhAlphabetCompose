package com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator

import com.aleksandrgenrikhs.nivkhalphabetcompose.Letters
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.mapper.FirstRevisionMapper
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.model.FirstRevisionModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.SelectUniqueElements
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever


class FirstRevisionUseCaseTest {
    private val letters = listOf(
        Letters.A, Letters.B, Letters.V, Letters.G
    )
    private val mappedLetters = listOf(
        FirstRevisionModel("Аа"),
        FirstRevisionModel("Бб"),
        FirstRevisionModel("Вв"),
        FirstRevisionModel("Гг"),
    )
    private val mapper: FirstRevisionMapper = spy(FirstRevisionMapper())
    private val selectUniqueElements: SelectUniqueElements = mock {
        on { invoke(any<List<Letters>>(), any(), any<(Letters) -> Any>()) }
            .doReturn(letters)
    }
    private val useCase = FirstRevisionUseCase(mapper, selectUniqueElements)

    @Test
    fun `WHEN call getLettersForFirstRevision THEN return list of mapped letters`() {
        val result = useCase.getLettersForFirstRevision()
        whenever(mapper.map(letters)).thenReturn(mappedLetters)
        assertEquals(mappedLetters, result)
        verify(mapper).map(letters)
        verifyNoMoreInteractions(mapper)
    }
}