package com.example.bioskopku.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bioskopku.di.Injection
import com.example.bioskopku.ui.ViewModelFactory
import com.example.bioskopku.ui.common.UiState
import com.example.bioskopku.ui.components.CartItem
import com.example.bioskopku.ui.components.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderFilms()
            }
            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { filmId, count ->
                        viewModel.updateOrderFilm(filmId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        com.example.bioskopku.R.string.share_message,
        state.orderFilm.count(),
        state.totalHarga
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            Text(
                text = stringResource(com.example.bioskopku.R.string.menu_cart),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        OrderButton(
            text = stringResource(com.example.bioskopku.R.string.total_harga, state.totalHarga),
            enabled = state.orderFilm.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.orderFilm, key = { it.film.id }) { item ->
                CartItem(
                    filmId = item.film.id,
                    photoUrl = item.film.photoUrl,
                    title = item.film.judul,
                    totalHarga = item.film.harga * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                )
                Divider()
            }
        }
    }
}