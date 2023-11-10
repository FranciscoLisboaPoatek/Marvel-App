package com.example.marvel_app.feature_character.data.local_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteCharacters")
class FavoriteCharacterDatabaseDTO(
    @PrimaryKey val id: String,
    @ColumnInfo val imgPath: String,
    @ColumnInfo val imgExtension: String,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String
)