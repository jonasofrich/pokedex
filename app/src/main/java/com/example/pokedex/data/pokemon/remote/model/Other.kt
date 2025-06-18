package com.example.pokedex.data.pokemon.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Other(
    @SerialName("official-artwork") var officialArtwork: OfficialArtwork = OfficialArtwork(),
)

@Serializable
data class OfficialArtwork(
    @SerialName("front_default") var frontDefault: String = ""
)