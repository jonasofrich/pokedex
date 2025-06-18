package com.example.pokedex.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun PokemonImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    val painter = rememberAsyncImagePainter(model = imageUrl)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (!isImageLoaded || painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }

        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (isImageLoaded) 1f else 0f),
        )

        LaunchedEffect(painter.state) {
            when (painter.state) {
                is AsyncImagePainter.State.Success -> {
                    isImageLoaded = true
                }
                is AsyncImagePainter.State.Error -> {
                    isImageLoaded = false
                    Log.e(
                        "PokemonImage",
                        "Failed to load image for $contentDescription: ${(painter.state as AsyncImagePainter.State.Error).result.throwable}"
                    )
                }
                is AsyncImagePainter.State.Loading -> {
                    isImageLoaded = false
                }
                AsyncImagePainter.State.Empty -> {
                    isImageLoaded = false
                }
            }
        }
    }
}