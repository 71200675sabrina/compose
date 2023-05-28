package com.example.bioskopku.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bioskopku.data.FilmRepository
import com.example.bioskopku.ui.screen.cart.CartViewModel
import com.example.bioskopku.ui.screen.detail.DetailFilmViewModel
import com.example.bioskopku.ui.screen.home.HomeViewModel


class ViewModelFactory(private val repository: FilmRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailFilmViewModel::class.java)) {
            return DetailFilmViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}