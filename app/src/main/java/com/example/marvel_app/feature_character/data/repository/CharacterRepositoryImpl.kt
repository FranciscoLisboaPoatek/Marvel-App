package com.example.marvel_app.feature_character.data.repository

import com.example.marvel_app.feature_character.data.CharacterMapper
import com.example.marvel_app.feature_character.data.network.CharacterApi
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterApi: CharacterApi
) : CharacterRepository{
    override suspend fun getDiscoverCharactersList(offset:Int): List<Character> {
        return characterApi.getCharacters(offset).data.results.map {
            CharacterMapper.characterApiDTOtoCharacter(it)
        }
    }
}