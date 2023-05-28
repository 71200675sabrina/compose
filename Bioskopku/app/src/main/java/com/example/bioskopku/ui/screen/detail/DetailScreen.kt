package com.example.bioskopku.ui.screen.detail

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.bioskopku.di.Injection
import com.example.bioskopku.ui.ViewModelFactory
import com.example.bioskopku.ui.common.UiState
import com.example.bioskopku.ui.components.OrderButton
import com.example.bioskopku.ui.components.ProductCounter
import com.example.bioskopku.ui.theme.BioskopkuTheme

@Composable
fun DetailScreen(
    filmId: Long,
    viewModel: DetailFilmViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFilmById(filmId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.film.photoUrl,
                    data.film.judul,
                    data.film.harga,
                    data.film.desc,
                    data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.film, count)
                        navigateToCart()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    photoUrl: String,
    title: String,
    basePoint: Int,
    descriprion : String,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPoint by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = com.example.bioskopku.R.drawable.placeholder),
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(com.example.bioskopku.R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = stringResource(com.example.bioskopku.R.string.harga_tiket, basePoint),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = stringResource(com.example.bioskopku.R.string.description, descriprion ),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.LightGray))
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                1,
                orderCount,
                onProductIncreased = { orderCount++ },
                onProductDecreased = { if (orderCount > 0) orderCount-- },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPoint = basePoint * orderCount
            OrderButton(
                text = stringResource(com.example.bioskopku.R.string.add_to_cart, totalPoint),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    BioskopkuTheme {
        DetailContent(
            "https://upload.wikimedia.org/wikipedia/en/f/f2/Fast_X_poster.jpg",
            "Faxt X",
            40000,
            "Dalam sinopsis Fast X menceritakan tim tersebut harus terbang ke Roma untuk melancarkan aksinya. Roma dan Han pun mengendarai mobil dan mengejar target di jalanan. Sementara itu, Tej dan Ramsey memantau dari kejauhan, berperan sebagai navigator untuk memastikan misi berjalan aman",
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}