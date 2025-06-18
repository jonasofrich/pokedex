package com.example.pokedex.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex.data.pokemon.local.PokemonDatabase
import com.example.pokedex.data.pokemon.local.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(
        @ApplicationContext appContext: Context
    ) : PokemonDatabase {
        return Room.databaseBuilder(
            appContext,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()
    }

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao {
        return database.pokemonDao()
    }
}