package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.CertificateUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FILE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
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

    suspend fun saveName(name: String) {
        interactor.saveName(name)
    }

    suspend fun generateCertificate(name: String) {
        _uiState.update { state ->
            state.copy(loading = true)
        }
        val result = alphabetRepository.generateCertificatePdf(name)
        when {
            result.isSuccess -> {
                _uiState.update { state ->
                    state.copy(
                        pdfPath = result.getOrNull() ?: "",
                        loading = false
                    )
                }
            }

            result.isFailure -> {
                _uiState.update { state ->
                    state.copy(
                        error = true,
                        errorMessage = result.exceptionOrNull()?.message ?: "unknown error",
                        loading = false
                    )
                }
            }
        }
    }

    suspend fun rendersCertificate(pdfFile: File): Bitmap? {
        return alphabetRepository.renderPdfPage(pdfFile)
    }

    fun downloadCertificate(pdfPath: String): Result<Unit> {
        return alphabetRepository.downloadCertificatePdf(pdfPath, FILE_NAME)
    }
}