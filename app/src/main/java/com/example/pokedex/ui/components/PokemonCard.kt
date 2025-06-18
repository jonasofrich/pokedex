package com.example.pokedex.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.domain.model.Pokemon
import kotlin.math.roundToInt

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onToggleFavorite: (Int) -> Unit,
    onRefresh: (() -> Unit)?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        PokemonImage(
            imageUrl = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.height_cm, pokemon.height * 10),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(R.string.weight_kg, (pokemon.weight * 0.1).roundToInt()),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            IconButton(
                onClick = {
                    onToggleFavorite(pokemon.id)
                }
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.secondary,
                    imageVector = if (pokemon.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (pokemon.isFavorite) stringResource(R.string.remove_from_favorites) else stringResource(
                        R.string.add_to_favorites
                    ),
                    modifier = Modifier.size(64.dp)
                )
            }
            if (onRefresh != null) {
                Spacer(Modifier.width(8.dp))
                IconButton(
                    onClick = { onRefresh() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
    }
}