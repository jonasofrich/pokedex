package com.example.pokedex.data.pokemon

import com.example.pokedex.data.pokemon.extensions.isExpired
import com.example.pokedex.data.pokemon.extensions.toEntity
import com.example.pokedex.data.pokemon.extensions.toPokemon
import com.example.pokedex.data.pokemon.local.PokemonLocalDataSource
import com.example.pokedex.data.pokemon.remote.PokemonRemoteDataSource
import com.example.pokedex.domain.model.Pokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.max
import kotlin.random.Random

interface PokemonRepository {
    suspend fun getRandomPokemon(): Pokemon
    suspend fun getPokemonById(id: Int): Pokemon
    val favorites: Flow<List<Pokemon>>
    suspend fun toggleFavorite(id: Int)
    suspend fun getPokemonPaginated(offset: Int): List<Pokemon>
}

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    override val favorites: Flow<List<Pokemon>>
        get() = pokemonLocalDataSource.favorites.map { entities ->
            entities.map { entity ->
                entity.toPokemon()
            }
        }

    override suspend fun getPokemonPaginated(offset: Int): List<Pokemon> = coroutineScope {
        val limit = 50
        val speciesBatch = pokemonRemoteDataSource.getBatch(limit, offset)
        return@coroutineScope speciesBatch.results
            .chunked(10)
            .flatMap { chunk ->
                chunk.map { item ->
                    async {
                        val species = pokemonRemoteDataSource.getPokemonSpecies(item.url)
                        val localPokemon = pokemonLocalDataSource.getPokemonById(species.id)
                        if (localPokemon != null && !localPokemon.isExpired()) {
                            localPokemon.toPokemon()
                        } else {
                            val remotePokemon =
                                pokemonRemoteDataSource.getPokemonById(species.id).toEntity()
                            pokemonLocalDataSource.insert(remotePokemon)
                            remotePokemon.toPokemon()
                        }
                        localPokemon?.toPokemon()
                            ?: pokemonRemoteDataSource.getPokemonById(species.id).toEntity()
                                .toPokemon()
                    }
                }
            }.awaitAll()
    }

    override suspend fun getRandomPokemon(): Pokemon {
        val remoteCount = pokemonRemoteDataSource.count()
        val localCount = pokemonLocalDataSource.count()
        val randomId = Random.nextInt(1, max(remoteCount, localCount) + 1)
        val localPokemon = pokemonLocalDataSource.getPokemonById(randomId)
        if (localPokemon == null) {
            val remotePokemon = pokemonRemoteDataSource.getPokemonById(randomId)
            val pokemonEntity = remotePokemon.toEntity()
            pokemonLocalDataSource.insert(pokemonEntity)
            return pokemonEntity.toPokemon()
        } else if (localPokemon.isExpired()) {
            val remotePokemon = pokemonRemoteDataSource.getPokemonById(randomId)
            val pokemonEntity = remotePokemon.toEntity()
            pokemonLocalDataSource.update(pokemonEntity)
            return pokemonEntity.toPokemon()
        } else {
            return localPokemon.toPokemon()
        }
    }

    override suspend fun getPokemonById(id: Int): Pokemon {
        val localPokemon = pokemonLocalDataSource.getPokemonById(id)
        if (localPokemon == null) {
            val remotePokemon = pokemonRemoteDataSource.getPokemonById(id)
            val pokemonEntity = remotePokemon.toEntity()
            pokemonLocalDataSource.insert(pokemonEntity)
            return pokemonEntity.toPokemon()
        } else if (localPokemon.isExpired()) {
            val remotePokemon = pokemonRemoteDataSource.getPokemonById(id)
            val pokemonEntity = remotePokemon.toEntity()
            pokemonLocalDataSource.update(pokemonEntity)
            return pokemonEntity.toPokemon()
        } else {
            return localPokemon.toPokemon()
        }
    }

    override suspend fun toggleFavorite(id: Int) {
        pokemonLocalDataSource.getPokemonById(id)?.let {
            it.isFavorite = !it.isFavorite
            pokemonLocalDataSource.update(it)
        }
    }
}