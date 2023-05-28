package com.example.bioskopku.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioskopku.data.FilmRepository
import com.example.bioskopku.model.OrderFilm
import com.example.bioskopku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FilmRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderFilm>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderFilm>>>
        get() = _uiState

    fun getAllFilm() {
        viewModelScope.launch {
            repository.getAllFilm()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderFilm ->
                    _uiState.value = UiState.Success(orderFilm)
                }
        }
    }
}