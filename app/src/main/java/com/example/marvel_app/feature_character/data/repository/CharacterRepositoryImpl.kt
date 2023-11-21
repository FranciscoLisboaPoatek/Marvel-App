package com.example.marvel_app.feature_character.data.repository

import com.example.marvel_app.feature_character.data.CharacterMapper
import com.example.marvel_app.feature_character.data.local_database.CharacterDao
import com.example.marvel_app.feature_character.data.network.CharacterApi
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.models.CharactersWrapper
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterApi: CharacterApi,
    private val characterDao: CharacterDao
) : CharacterRepository {
    override suspend fun getDiscoverCharactersList(offset: Int,orderBy:String ,name: String?): CharactersWrapper {
        val characterApiDTO = characterApi.getCharacters(offset, orderBy, name)

        return CharactersWrapper(
            characterApiDTO.data.total,
            characterApiDTO.data.count,
            characterApiDTO.data.results.map {
                CharacterMapper.characterApiDtoToCharacter(it)
            })
    }

    override suspend fun getFavoriteCharactersList(): List<Character> =
        withContext(Dispatchers.IO) {
            return@withContext characterDao.getAllFavoriteCharacters().map {
                CharacterMapper.favoriteCharacterDatabaseDtoToCharacter(it)
            }
        }

    override suspend fun searchFavoriteCharacters(name: String): List<Character> =
       withContext(Dispatchers.IO){
           return@withContext characterDao.searchFavoriteCharactersByName(name).map {
               CharacterMapper.favoriteCharacterDatabaseDtoToCharacter(it)
           }
       }


    override suspend fun markAsFavoriteCharacter(character: Character) {
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteCharacter(
                CharacterMapper.characterToFavoriteCharacterDatabaseDto(
                    character
                )
            )
        }
    }

    override suspend fun removeFavoriteCharacter(character: Character) {
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteCharacter(
                CharacterMapper.characterToFavoriteCharacterDatabaseDto(
                    character
                )
            )
        }
    }
}