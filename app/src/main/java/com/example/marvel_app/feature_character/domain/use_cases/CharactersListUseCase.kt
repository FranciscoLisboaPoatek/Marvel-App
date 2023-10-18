package com.example.marvel_app.feature_character.domain.use_cases

import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository

class CharactersListUseCase (
    private val repository: CharacterRepository
){
    suspend fun execute(offset:Int, name:String?): List<Character>{
        return repository.getDiscoverCharactersList(offset,name)
    }
}