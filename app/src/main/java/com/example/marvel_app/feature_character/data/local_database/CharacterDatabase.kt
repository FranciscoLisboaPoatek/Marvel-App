package com.example.marvel_app.feature_character.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacterDatabaseDTO::class], version = 1)
abstract class CharacterDatabase:RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}