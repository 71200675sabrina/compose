package com.example.bioskopku.ui.screen.cart

import com.example.bioskopku.model.OrderFilm

data class CartState (
    val orderFilm: List<OrderFilm>,
    val totalHarga : Int
        )