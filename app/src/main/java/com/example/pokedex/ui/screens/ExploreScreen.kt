package com.example.pokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokedex.ui.components.PokemonListItem
import com.example.pokedex.ui.viewmodel.ExploreUiState
import com.example.pokedex.ui.viewmodel.ExploreViewModel

@Composable
fun ExploreScreen(modifier: Modifier = Modifier, onPokemonClick: (Int) -> Unit) {
    val viewModel: ExploreViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = lazyListState.layoutInfo.totalItemsCount
                if (lastVisibleIndex != null && lastVisibleIndex >= totalItems - 5) {
                    if (uiState is ExploreUiState.Success && (uiState as ExploreUiState.Success).list.isNotEmpty()) {
                        val currentList = (uiState as ExploreUiState.Success).list
                        val lastPokemonId = currentList.lastOrNull()?.id
                        lastPokemonId?.let {
                            viewModel.getPokemonPaginated(it)
                        }
                    }
                }
            }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is ExploreUiState.Loading -> {
                CircularProgressIndicator()
            }

            is ExploreUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = lazyListState
                ) {
                    items(state.list) { pokemon ->
                        PokemonListItem(
                            pokemon = pokemon,
                            onFavoriteClick = null,
                            onItemClick = { id -> onPokemonClick(id) }
                        )
                    }
                    if (state.isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }

            is ExploreUiState.Error -> {
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}