package com.example.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokedex.R
import com.example.pokedex.ui.components.PokemonCard
import com.example.pokedex.ui.viewmodel.DetailsUiState
import com.example.pokedex.ui.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(pokemonId: Int, onNavigateBack: () -> Unit) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(pokemonId) {
        viewModel.getPokemonById(pokemonId)
    }
    Column {
        IconButton(
            onClick = { onNavigateBack() },
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp)
                .width(64.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (val state = uiState) {
                is DetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is DetailsUiState.Success -> {
                    PokemonCard(
                        pokemon = state.pokemon,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onRefresh = null
                    )
                }

                is DetailsUiState.Error -> {
                    Text(text = state.message)
                }
            }
        }
    }
}