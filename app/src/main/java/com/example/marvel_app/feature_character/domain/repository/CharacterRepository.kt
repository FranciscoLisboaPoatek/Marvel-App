package com.example.marvel_app.feature_character.domain.repository

import com.example.marvel_app.feature_character.domain.models.Character

interface CharacterRepository {
    suspend fun getDiscoverCharactersList(offset: Int,name:String?): List<Character>
}