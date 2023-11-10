package com.example.marvel_app.feature_character.domain.models

data class CharactersWrapper (
    val listTotal: Int,
    val listCount: Int,
    val charactersList: List<Character>
)