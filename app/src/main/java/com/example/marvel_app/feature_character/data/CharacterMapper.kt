package com.example.marvel_app.feature_character.data

import com.example.marvel_app.feature_character.data.network.CharacterApiDTO
import com.example.marvel_app.feature_character.domain.models.Character

class CharacterMapper {
    companion object {
        fun characterApiDTOtoCharacter(characterApiDTO: CharacterApiDTO): Character {
            return Character(
                id = characterApiDTO.id.toString(),
                name = characterApiDTO.name,
                description = characterApiDTO.description,
                imgPath = characterApiDTO.thumbnail.path,
                imgExtension = characterApiDTO.thumbnail.extension
            )
        }
    }
}