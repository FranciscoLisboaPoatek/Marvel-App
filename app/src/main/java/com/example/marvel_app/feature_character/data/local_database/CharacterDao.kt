package com.example.marvel_app.feature_character.data.local_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM FavoriteCharacters")//implement offset
    fun getAllFavoriteCharacters(): List<FavoriteCharacterDatabaseDTO>

    @Insert
    fun insertFavoriteCharacter(favoriteCharacterDatabaseDTO: FavoriteCharacterDatabaseDTO)

    @Delete
    fun deleteFavoriteCharacter(favoriteCharacterDatabaseDTO: FavoriteCharacterDatabaseDTO)
}