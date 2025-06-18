package com.example.pokedex.data.pokemon.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val updatedTimestamp: Long,
    var isFavorite: Boolean
)