package com.example.pokedex.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.pokemon.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPokemonById(id: Int) {
        viewModelScope.launch {
            try {
                val pokemon = pokemonRepository.getPokemonById(id)
                _uiState.update { DetailsUiState.Success(pokemon = pokemon) }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching pokemon", e)
                _uiState.update { DetailsUiState.Error("Something went wrong") }
            }
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            pokemonRepository.toggleFavorite(id)
            _uiState.update {
                when (it) {
                    is DetailsUiState.Success -> it.copy(pokemon = it.pokemon.copy(isFavorite = !it.pokemon.isFavorite))
                    else -> it
                }
            }
        }
    }
}