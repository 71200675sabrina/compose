package com.example.bioskopku.di

import com.example.bioskopku.data.FilmRepository

object Injection {
    fun provideRepository(): FilmRepository {
        return FilmRepository.getInstance()
    }
}