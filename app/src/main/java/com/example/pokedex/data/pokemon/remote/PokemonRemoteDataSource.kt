package com.example.pokedex.data.pokemon.remote

import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val apiInterface: PokeApiInterface
) {
    suspend fun count(): Int {
        return apiInterface.getPokemonCount().count
    }

    suspend fun getPokemonSpecies(url: String) = apiInterface.getPokemonSpecies(url)

    suspend fun getPokemonById(id: Int) = apiInterface.getPokemonById(id)

    suspend fun getBatch(limit: Int, offset: Int) = apiInterface.getBatch(limit, offset)
}