package com.example.pokedex.data.pokemon.remote

import PokemonCount
import com.example.pokedex.data.pokemon.remote.model.PokemonSpecies
import com.example.pokedex.data.pokemon.remote.model.RemotePokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApiInterface {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): RemotePokemon

    @GET("pokemon-species")
    suspend fun getPokemonCount(): PokemonCount

    @GET("pokemon-species")
    suspend fun getBatch(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonCount

    @GET
    suspend fun getPokemonSpecies(@Url url: String): PokemonSpecies
}