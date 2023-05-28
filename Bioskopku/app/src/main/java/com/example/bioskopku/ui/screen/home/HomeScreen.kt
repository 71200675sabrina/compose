package com.example.bioskopku.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bioskopku.di.Injection
import com.example.bioskopku.model.OrderFilm
import com.example.bioskopku.ui.ViewModelFactory
import com.example.bioskopku.ui.common.UiState
import com.example.bioskopku.ui.components.FilmListItem


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFilm()
            }
            is UiState.Success -> {
                HomeContent(
                    orderFilm = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderFilm: List<OrderFilm>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(orderFilm){data ->
            FilmListItem(photoUrl = data.film.photoUrl, judul = data.film.judul , harga = data.film.harga,
            modifier = Modifier.clickable { navigateToDetail(data.film.id) })
        }
    }
}