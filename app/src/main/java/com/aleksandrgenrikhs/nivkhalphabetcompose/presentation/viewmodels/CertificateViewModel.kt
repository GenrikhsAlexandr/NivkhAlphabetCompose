package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.CertificateUIState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.Constants.FILE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CertificateViewModel
@Inject constructor(
    private val interactor: PrefInteractor,
    private val repository: AlphabetRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<CertificateUIState> =
        MutableStateFlow(CertificateUIState())
    val uiState = _uiState.asStateFlow()

    private val createPdf: MutableStateFlow<ByteArray> = MutableStateFlow(byteArrayOf())

    fun setName(name: String) {
        _uiState.update {
            _uiState.value.copy(
                name = name,
            )
        }
    }

    suspend fun saveName(name: String) {
        interactor.saveName(name)
    }

    fun downloadCertificate(): Result<Unit> {
        return repository.downloadCertificatePdf(createPdf.value, FILE_NAME)
    }
}