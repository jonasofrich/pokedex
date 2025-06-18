package com.example.pokedex.data.pokemon.local

import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao
) {
    suspend fun count(): Int {
        return pokemonDao.count()
    }

    suspend fun getPokemonById(id: Int) = pokemonDao.getById(id)

    suspend fun insert(pokemonEntity: PokemonEntity) {
        pokemonDao.insert(pokemonEntity)
    }

    suspend fun update(pokemonEntity: PokemonEntity) = pokemonDao.update(pokemonEntity)

    val favorites = pokemonDao.getFavoritesAsFlow()
}