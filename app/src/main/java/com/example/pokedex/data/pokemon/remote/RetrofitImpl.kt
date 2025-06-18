package com.example.pokedex.data.pokemon.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitImpl {

    private val retroJson = Json { ignoreUnknownKeys = true }

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(
                retroJson.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            ))
            .build()
    }

    val apiInterface: PokeApiInterface by lazy {
        retrofit.create(PokeApiInterface::class.java)
    }
}