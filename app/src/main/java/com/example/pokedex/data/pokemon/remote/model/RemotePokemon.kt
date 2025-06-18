package com.example.pokedex.data.pokemon.remote.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RemotePokemon(
    @SerialName("height") val height: Int = 0,
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("sprites") val sprites: Sprites = Sprites(),
    @SerialName("weight") val weight: Int = 0
)