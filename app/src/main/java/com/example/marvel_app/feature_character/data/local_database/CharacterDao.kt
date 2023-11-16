package com.example.marvel_app.feature_character.data.local_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * FROM FavoriteCharacters")//implement offset
    fun getAllFavoriteCharacters(): List<FavoriteCharacterDatabaseDTO>

    @Query("SELECT * FROM FavoriteCharacters WHERE name LIKE :searchName")
    fun searchFavoriteCharactersByName(searchName: String): List<FavoriteCharacterDatabaseDTO>
    @Query("SELECT * FROM FavoriteCharacters WHERE id LIKE :id")
    fun searchCharacterById(id:String):FavoriteCharacterDatabaseDTO
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteCharacter(favoriteCharacterDatabaseDTO: FavoriteCharacterDatabaseDTO)

    @Delete
    fun deleteFavoriteCharacter(favoriteCharacterDatabaseDTO: FavoriteCharacterDatabaseDTO)
}