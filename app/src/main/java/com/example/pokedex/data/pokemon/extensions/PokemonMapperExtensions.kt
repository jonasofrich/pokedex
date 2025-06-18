package com.example.pokedex.data.pokemon.extensions

import com.example.pokedex.data.pokemon.local.PokemonEntity
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.data.pokemon.remote.model.RemotePokemon
import kotlin.time.Duration.Companion.days

fun PokemonEntity.toPokemon() = Pokemon(
    id = this.id,
    name = this.name,
    height = this.height,
    weight = this.weight,
    imageUrl = this.imageUrl,
    isFavorite = this.isFavorite
)


fun RemotePokemon.toEntity() = PokemonEntity(
    id = this.id,
    name = this.name,
    height = this.height,
    weight = this.weight,
    imageUrl = this.sprites.other.officialArtwork.frontDefault,
    updatedTimestamp = System.currentTimeMillis(),
    isFavorite = false
)

fun PokemonEntity.isExpired() = (System.currentTimeMillis() - this.updatedTimestamp) > 7.days.inWholeMilliseconds