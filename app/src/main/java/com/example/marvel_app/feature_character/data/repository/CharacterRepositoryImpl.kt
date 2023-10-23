package com.example.marvel_app.feature_character.data.repository

import com.example.marvel_app.feature_character.data.CharacterMapper
import com.example.marvel_app.feature_character.data.network.CharacterApi
import com.example.marvel_app.feature_character.data.network.CharacterApiDTO
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.models.CharactersWrapper
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterApi: CharacterApi
) : CharacterRepository{
    override suspend fun getDiscoverCharactersList(offset:Int,name: String?): CharactersWrapper {


        val characterApiDTO = characterApi.getCharacters(offset, name)

        return CharactersWrapper(
            characterApiDTO.data.total,
            characterApiDTO.data.count,
            characterApiDTO.data.results.map {
            CharacterMapper.characterApiDTOtoCharacter(it)
        })
    }
}