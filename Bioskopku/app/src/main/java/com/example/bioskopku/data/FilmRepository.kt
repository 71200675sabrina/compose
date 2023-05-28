package com.example.bioskopku.data

import com.example.bioskopku.model.DataFilm
import com.example.bioskopku.model.OrderFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FilmRepository {
    private val orderFilm = mutableListOf<OrderFilm>()

    init {
        if (orderFilm.isEmpty()){
            DataFilm.film.forEach {
                orderFilm.add(OrderFilm(it,0))
            }
        }
    }

    fun getAllFilm():Flow<List<OrderFilm>> {
        return flowOf(orderFilm)
    }

    fun getOrderFilmById(filmId : Long) : OrderFilm {
        return orderFilm.first {
            it.film.id == filmId
        }
    }

    fun updateOrderFilm(filmId: Long, newCountValue : Int): Flow<Boolean>{
        val index = orderFilm.indexOfFirst { it.film.id == filmId }
        val result = if (index >= 0) {
            val orderFilms = orderFilm[index]
            orderFilm[index] = orderFilms.copy(film = orderFilms.film, count = newCountValue )
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderFilm() : Flow<List<OrderFilm>> {
        return getAllFilm()
            .map { orderFilm -> orderFilm.filter { orderFilm ->
                orderFilm.count != 0
            }
            }
    }

    companion object{
        @Volatile
        private var instance : FilmRepository? = null

        fun getInstance(): FilmRepository = instance ?: synchronized(this){
            FilmRepository().apply {
                instance = this
            }
        }
    }
}