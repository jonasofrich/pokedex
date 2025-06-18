package com.example.pokedex.data.pokemon.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}