package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokedex.ui.components.PokemonCard
import com.example.pokedex.ui.viewmodel.DetailsUiState
import com.example.pokedex.ui.viewmodel.RandomViewModel

@Composable
fun RandomScreen() {
    val viewModel: RandomViewModel = hiltViewModel()
    DisposableEffect(key1 = viewModel) {
        viewModel.getRandomPokemon()
        onDispose {
            viewModel.onScreenExit()
        }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (val state = uiState) {
                is DetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is DetailsUiState.Success -> {
                    PokemonCard(
                        pokemon = state.pokemon,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onRefresh = { viewModel.getRandomPokemon()}
                    )
                }

                is DetailsUiState.Error -> {
                    Text(text = state.message)
                }
            }
        }
    }
}