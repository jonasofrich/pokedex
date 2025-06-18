package com.example.pokedex.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    onFavoriteClick: ((Int) -> Unit)?
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(pokemon.id) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonImage(
                imageUrl = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "#${pokemon.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (onFavoriteClick != null) {
                IconButton(
                    onClick = {
                        onFavoriteClick.invoke(pokemon.id)
                    }
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.secondary,
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(R.string.remove_from_favorites)
                    )
                }
            }
        }
    }
}