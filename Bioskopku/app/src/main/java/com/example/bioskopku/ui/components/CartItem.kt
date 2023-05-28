package com.example.bioskopku.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bioskopku.R
import com.example.bioskopku.ui.theme.BioskopkuTheme
import com.example.bioskopku.ui.theme.Shapes

@Composable
fun CartItem(
    filmId: Long,
    photoUrl: String,
    title: String,
    totalHarga: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    com.example.bioskopku.R.string.total_harga,
                    totalHarga
                ),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        ProductCounter(
            orderId = filmId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(filmId, count + 1) },
            onProductDecreased = { onProductCountChanged(filmId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    BioskopkuTheme {
        CartItem(
            2,"https://upload.wikimedia.org/wikipedia/en/f/f2/Fast_X_poster.jpg","Fast X", 40000,0,
            onProductCountChanged = { filmId, count -> },
        )
    }
}