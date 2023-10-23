package com.example.marvel_app.feature_character.domain.use_cases

import com.example.marvel_app.feature_character.domain.models.CharactersListUseCaseResponse
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository

class CharactersListUseCase (
    private val repository: CharacterRepository
){
    suspend fun execute(offset:Int, name:String?): CharactersListUseCaseResponse {
        val charactersWrapper = repository.getDiscoverCharactersList(offset,name)
        val listEnded = offset + charactersWrapper.listCount >= charactersWrapper.listTotal
        return CharactersListUseCaseResponse(
            listEnded,
            charactersWrapper.charactersList
        )

    }
}