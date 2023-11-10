package com.example.marvel_app.feature_character.domain.repository

import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.models.CharactersWrapper

interface CharacterRepository {
    suspend fun getDiscoverCharactersList(offset: Int,name:String?): CharactersWrapper
    suspend fun getFavoriteCharactersList():List<Character>
    suspend fun searchFavoriteCharacters(name: String):List<Character>
    suspend fun markAsFavoriteCharacter(character: Character)
    suspend fun removeFavoriteCharacter(character: Character)
}