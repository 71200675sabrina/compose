package com.example.bioskopku.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
fun FilmListItem(
    photoUrl: String,
    judul: String,
    harga: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .size(170.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = judul,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = stringResource(R.string.harga_tiket, harga),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FilmItemPreview() {
    BioskopkuTheme{
        FilmListItem("https://upload.wikimedia.org/wikipedia/en/f/f2/Fast_X_poster.jpg", "Fast X", 40000)
    }
}