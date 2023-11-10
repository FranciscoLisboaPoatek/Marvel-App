package com.example.marvel_app.feature_character.domain.use_cases

import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository

class FavoriteCharactersListUseCase(
    private val repository: CharacterRepository
) {
    suspend fun favoriteCharactersList():List<Character>{
        return repository.getFavoriteCharactersList()
    }

    suspend fun searchFavoriteCharacters(name:String):List<Character>{
        return repository.searchFavoriteCharacters(name.plus("%"))
    }
}