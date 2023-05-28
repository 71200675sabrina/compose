package com.example.bioskopku.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioskopku.data.FilmRepository
import com.example.bioskopku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: FilmRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderFilms() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderFilm()
                .collect { orderFilm ->
                    val totalRequiredPoint =
                        orderFilm.sumOf { it.film.harga * it.count }
                    _uiState.value = UiState.Success(CartState(orderFilm, totalRequiredPoint))
                }
        }
    }

    fun updateOrderFilm(filmId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFilm(filmId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderFilms()
                    }
                }
        }
    }
}