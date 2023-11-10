package com.example.marvel_app.feature_character.data

import com.example.marvel_app.feature_character.data.local_database.FavoriteCharacterDatabaseDTO
import com.example.marvel_app.feature_character.data.network.CharacterApiDTO
import com.example.marvel_app.feature_character.domain.models.Character

class CharacterMapper {
    companion object {
        fun characterApiDtoToCharacter(characterApiDTO: CharacterApiDTO): Character {
            return Character(
                id = characterApiDTO.id.toString(),
                name = characterApiDTO.name,
                description = characterApiDTO.description,
                imgPath = characterApiDTO.thumbnail.path,
                imgExtension = characterApiDTO.thumbnail.extension
            )
        }

        fun favoriteCharacterDatabaseDtoToCharacter(favoriteCharacterDatabaseDTO: FavoriteCharacterDatabaseDTO): Character {
            return Character(
                id = favoriteCharacterDatabaseDTO.id,
                name = favoriteCharacterDatabaseDTO.name,
                description = favoriteCharacterDatabaseDTO.description,
                imgPath = favoriteCharacterDatabaseDTO.imgPath,
                imgExtension = favoriteCharacterDatabaseDTO.imgExtension,
                isFavorited = true
            )
        }

        fun characterToFavoriteCharacterDatabaseDto(character: Character): FavoriteCharacterDatabaseDTO {
            return FavoriteCharacterDatabaseDTO(
                id = character.id,
                name = character.name,
                description = character.description,
                imgPath = character.imgPath,
                imgExtension = character.imgExtension
            )
        }
    }
}