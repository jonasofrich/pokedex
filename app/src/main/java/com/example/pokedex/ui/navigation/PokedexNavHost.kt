package com.example.pokedex.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedex.R
import com.example.pokedex.ui.screens.DetailsScreen
import com.example.pokedex.ui.screens.ExploreScreen
import com.example.pokedex.ui.screens.FavoritesScreen
import com.example.pokedex.ui.screens.RandomScreen

@Composable
fun PokedexNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onPokemonClick: (Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Random.route,
        modifier = modifier
    ) {
        composable(Screen.Random.route) {
            RandomScreen()
        }
        composable(Screen.Explore.route) {
            ExploreScreen(
                modifier = Modifier.fillMaxSize(),
                onPokemonClick = onPokemonClick
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onPokemonClick = onPokemonClick
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("pokemonId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            if (pokemonId != null) {
                DetailsScreen(
                    pokemonId = pokemonId,
                    onNavigateBack = { navController.popBackStack() })
            } else {
                Text(stringResource(R.string.error_not_found))
            }
        }
    }
}