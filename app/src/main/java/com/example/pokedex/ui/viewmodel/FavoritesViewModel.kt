package com.example.pokedex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.pokemon.PokemonRepository
import com.example.pokedex.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val favoritesList: List<Pokemon>) : FavoritesUiState
    data class Error(val message: String) : FavoritesUiState
}

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            pokemonRepository.favorites.collect { favorites ->
                _uiState.update {
                    FavoritesUiState.Success(favoritesList = favorites)
                }
            }
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            pokemonRepository.toggleFavorite(id)
        }
    }
}