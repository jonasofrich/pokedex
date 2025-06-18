package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokedex.ui.components.PokemonListItem
import com.example.pokedex.ui.viewmodel.FavoritesUiState
import com.example.pokedex.ui.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier, onPokemonClick: (Int) -> Unit) {
    val viewModel: FavoritesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is FavoritesUiState.Loading -> {
                CircularProgressIndicator()
            }

            is FavoritesUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.favoritesList) { pokemon ->
                        PokemonListItem(
                            pokemon = pokemon,
                            onFavoriteClick = { id -> viewModel.toggleFavorite(id) },
                            onItemClick = { id -> onPokemonClick(id) }
                        )
                    }
                }
            }

            is FavoritesUiState.Error -> {
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}