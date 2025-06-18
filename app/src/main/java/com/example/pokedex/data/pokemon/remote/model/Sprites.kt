package com.example.pokedex.data.pokemon.remote.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Sprites(
    @SerialName("back_default") val backDefault: String? = null,
    @SerialName("front_default") val frontDefault: String? = null,
    val other: Other = Other()
)