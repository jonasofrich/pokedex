package com.example.pokedex.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.pokedex.ui.theme.GreyText
import com.example.pokedex.ui.theme.White

@Composable
fun BottomNavigationBar(
    navItems: List<NavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(containerColor = White) {
        navItems.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                label = { Text(navItem.label) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = GreyText,
                    unselectedTextColor = GreyText
                )
            )
        }
    }
}