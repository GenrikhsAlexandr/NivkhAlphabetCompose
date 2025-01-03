package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.graphics.Bitmap
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

class CertificateViewModelTest {

    private lateinit var viewModel: CertificateViewModel
    private val interactor: PrefInteractor = mock()
    private val alphabetRepository: AlphabetRepository = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = CertificateViewModel(
            interactor,
            alphabetRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN call rendersCertificate AND certificate is not null THEN saveIsCertificateCreated AND update uiState loading = false AND error = false`() =
        runTest {
            val name = "testName"
            val certificate: Bitmap = mock()
            whenever(alphabetRepository.getPdfPage(name)).thenReturn(certificate)

            val result = viewModel.rendersCertificate(name)
            assertFalse(viewModel.uiState.value.loading)
            assertFalse(viewModel.uiState.value.error)
            verify(alphabetRepository).getPdfPage(name)
            verify(interactor).saveIsCertificateCreated(true)
            assertEquals(certificate, result)
        }

    @Test
    fun `WHEN call rendersCertificate AND certificate is null THEN saveIsCertificateCreated AND update uiState loading = false AND error = true`() =
        runTest {
            val name = "testName"
            whenever(alphabetRepository.getPdfPage(name)).thenReturn(null)

            val result = viewModel.rendersCertificate(name)
            assertFalse(viewModel.uiState.value.loading)
            assertTrue(viewModel.uiState.value.error)
            assertNull(result)
            verifyNoInteractions(interactor)
        }
}
