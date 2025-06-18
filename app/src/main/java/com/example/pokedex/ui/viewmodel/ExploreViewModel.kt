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

sealed interface ExploreUiState {
    data object Loading : ExploreUiState
    data class Success(val list: List<Pokemon>, val isLoading: Boolean = false) : ExploreUiState
    data class Error(val message: String) : ExploreUiState
}

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExploreUiState>(ExploreUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getPokemonPaginated(0)
    }

    fun getPokemonPaginated(offset: Int) {
        viewModelScope.launch {
            if (_uiState.value is ExploreUiState.Success && (_uiState.value as ExploreUiState.Success).isLoading) {
                return@launch
            }
            if (_uiState.value is ExploreUiState.Success) {
                _uiState.value = (_uiState.value as ExploreUiState.Success).copy(isLoading = true)
            } else {
                _uiState.value = ExploreUiState.Loading
            }
            val list = pokemonRepository.getPokemonPaginated(offset)
            _uiState.update { currentState ->
                when (currentState) {
                    is ExploreUiState.Success -> {
                        val updatedList = currentState.list + list
                        currentState.copy(list = updatedList, isLoading = false)
                    }
                    else -> ExploreUiState.Success(list, isLoading = false)
                }
            }
        }
    }
}