package com.example.bioskopku.ui.navigation

sealed class Screen (val route: String){
    object Home : Screen("home")
    object Cart : Screen("cart")
    object About: Screen("about")
    object DetailFilm : Screen("home/{filmId}"){
        fun createRoute(filmId:Long) = "home/$filmId"
    }
}