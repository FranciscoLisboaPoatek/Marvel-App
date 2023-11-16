package com.example.marvel_app.feature_character.domain.use_cases

import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository

class FavoriteCharacterUseCase(
    private val repository: CharacterRepository
) {
    suspend fun execute(character: Character){
        if (character.isFavorited){
            repository.removeFavoriteCharacter(character)
        } else repository.markAsFavoriteCharacter(character)
    }

    suspend fun isCharacterFavorited(character: Character):Boolean{
        return repository.isCharacterFavorited(character.id)
    }
}