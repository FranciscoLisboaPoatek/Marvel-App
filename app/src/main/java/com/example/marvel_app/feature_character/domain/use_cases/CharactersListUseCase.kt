package com.example.marvel_app.feature_character.domain.use_cases

import com.example.marvel_app.feature_character.domain.models.CharactersListUseCaseResponse
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository

class CharactersListUseCase (
    private val repository: CharacterRepository
){
    suspend fun discoverCharactersList(offset:Int,orderBy:String, name:String?): CharactersListUseCaseResponse {
        val charactersWrapper = repository.getDiscoverCharactersList(offset,orderBy,name)
        val listEnded = offset + charactersWrapper.listCount >= charactersWrapper.listTotal
        return CharactersListUseCaseResponse(
            listEnded,
            charactersWrapper.charactersList
        )

    }
}