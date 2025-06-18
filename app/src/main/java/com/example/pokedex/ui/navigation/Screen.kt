package com.example.pokedex.ui.navigation

sealed class Screen(val route: String) {
    data object Explore : Screen("explore")
    data object Random : Screen("random")
    data object Favorites : Screen("favorites")
    data object Details : Screen("details/{pokemonId}?origin={origin}") {
        fun createRoute(pokemonId: Int) = "details/$pokemonId"
    }
}