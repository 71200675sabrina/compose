package com.example.bioskopku.ui.screen.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bioskopku.R

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
    ){
        Text(stringResource(R.string.menu_about))
    }
}