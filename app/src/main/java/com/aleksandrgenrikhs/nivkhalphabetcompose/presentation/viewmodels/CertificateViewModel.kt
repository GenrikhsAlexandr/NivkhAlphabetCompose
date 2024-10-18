package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.CertificateUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CertificateViewModel
@Inject constructor(
    private val interactor: PrefInteractor,
    private val alphabetRepository: AlphabetRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<CertificateUIState> =
        MutableStateFlow(CertificateUIState())
    val uiState = _uiState.asStateFlow()

    suspend fun rendersCertificate(name: String): Bitmap? {
        val certificate = alphabetRepository.getPdfPage(name)
        if (certificate != null) {
            interactor.saveIsCertificateCreated(true)

            _uiState.update { state ->
                state.copy(
                    loading = false
                )
            }
        } else {
            _uiState.update { state ->
                state.copy(
                    loading = false,
                    error = true,
                )
            }
        }
        return certificate
    }

    fun downloadCertificate(): Result<Unit> {
        return alphabetRepository.downloadCertificatePdf()
    }
}