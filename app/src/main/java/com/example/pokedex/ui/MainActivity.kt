package com.example.pokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.R
import com.example.pokedex.ui.navigation.BottomNavigationBar
import com.example.pokedex.ui.navigation.NavItem
import com.example.pokedex.ui.navigation.PokedexNavHost
import com.example.pokedex.ui.navigation.Screen
import com.example.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                val navController = rememberNavController()
                val navItems = listOf(
                    NavItem(
                        label = getString(R.string.random_screen_label),
                        icon = ImageVector.vectorResource(R.drawable.ic_shuffle),
                        route = Screen.Random.route
                    ),
                    NavItem(
                        label = getString(R.string.explore_screen_label),
                        icon = Icons.Default.Search,
                        route = Screen.Explore.route
                    ),
                    NavItem(
                        label = getString(R.string.favorites_screen_label),
                        icon = Icons.Default.Favorite,
                        route = Screen.Favorites.route
                    )
                )
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                var selectedNavItemIndex by remember {
                    mutableIntStateOf(navItems.indexOfFirst { it.route == currentRoute })
                }
                LaunchedEffect(currentRoute) {
                    val index = navItems.indexOfFirst { it.route == currentRoute }
                    if (index != -1 && index != selectedNavItemIndex) {
                        selectedNavItemIndex = index
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            navItems = navItems,
                            selectedIndex = selectedNavItemIndex,
                            onItemSelected = { index ->
                                navController.navigate(navItems[index].route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        PokedexNavHost(navController = navController, modifier = Modifier.fillMaxSize()) { pokemonId ->
                            navController.navigate(Screen.Details.createRoute(pokemonId))
                        }
                    }
                }
            }
        }
    }
}