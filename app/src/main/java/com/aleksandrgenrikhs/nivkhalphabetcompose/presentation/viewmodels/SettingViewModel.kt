package com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.interator.PrefInteractor
import com.aleksandrgenrikhs.nivkhalphabetcompose.presentation.uistate.SettingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
@Inject constructor(
    private val prefInteractor: PrefInteractor,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingUIState> =
        MutableStateFlow(SettingUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentValue = prefInteractor.getSoundEnabled()
            _uiState.update { state ->
                state.copy(isSoundEnable = currentValue)
            }
        }
    }

    fun setSoundEnable(value: Boolean) {
        viewModelScope.launch {
            prefInteractor.saveSoundEnabled(value)
            _uiState.update { state ->
                state.copy(isSoundEnable = value)
            }
        }
    }
}
