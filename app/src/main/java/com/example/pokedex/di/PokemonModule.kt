package com.example.pokedex.di

import com.example.pokedex.data.pokemon.PokemonRepository
import com.example.pokedex.data.pokemon.PokemonRepositoryImpl
import com.example.pokedex.data.pokemon.local.PokemonDao
import com.example.pokedex.data.pokemon.local.PokemonLocalDataSource
import com.example.pokedex.data.pokemon.remote.PokeApiInterface
import com.example.pokedex.data.pokemon.remote.PokemonRemoteDataSource
import com.example.pokedex.data.pokemon.remote.RetrofitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PokemonModule {
    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonLocalDataSource: PokemonLocalDataSource,
        pokemonRemoteDataSource: PokemonRemoteDataSource
    ) : PokemonRepository {
        return PokemonRepositoryImpl(
            pokemonLocalDataSource, pokemonRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(pokemonDao: PokemonDao): PokemonLocalDataSource {
        return PokemonLocalDataSource(pokemonDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiInterface: PokeApiInterface
    ): PokemonRemoteDataSource {
        return PokemonRemoteDataSource(
            apiInterface
        )
    }

    @Provides
    @Singleton
    fun provideApiInterface(): PokeApiInterface {
        return RetrofitImpl.apiInterface
    }
}