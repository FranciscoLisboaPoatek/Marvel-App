package com.example.marvel_app.feature_character.domain.models

data class CharactersListUseCaseResponse(
    val listEnded : Boolean,
    val charactersList: List<Character>
) {
}