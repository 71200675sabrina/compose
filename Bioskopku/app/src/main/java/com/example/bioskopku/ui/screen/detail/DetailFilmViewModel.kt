package com.example.bioskopku.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bioskopku.data.FilmRepository
import com.example.bioskopku.model.Film
import com.example.bioskopku.model.OrderFilm
import com.example.bioskopku.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailFilmViewModel (private val repository: FilmRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderFilm>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderFilm>>
        get() = _uiState

    fun getFilmById(filmId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderFilmById(filmId))
        }
    }

    fun addToCart(film: Film, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFilm(film.id, count)
        }
    }

}

