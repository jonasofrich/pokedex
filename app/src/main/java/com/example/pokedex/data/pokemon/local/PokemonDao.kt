package com.example.pokedex.data.pokemon.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun batchInsert(pokemon: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonEntity: PokemonEntity)

    @Update
    suspend fun update(pokemonEntity: PokemonEntity)

    @Query("SELECT COUNT(id) FROM pokemonentity")
    suspend fun count(): Int

    @Query("SELECT * FROM pokemonentity WHERE id = :id")
    suspend fun getById(id: Int): PokemonEntity?

    @Query("SELECT * FROM pokemonentity WHERE isFavorite = '1'")
    fun getFavoritesAsFlow(): Flow<List<PokemonEntity>>
}