package com.example.marvel_app.feature_character.data.network

data class ApiResponse(
    val data: DataResult
)

data class DataResult(
    val results: List<CharacterApiDTO>
)

data class CharacterApiDTO(
    val id: Int,
    val name: String,
    val description: String,
    val urls: List<Url>
)

data class Url(
    val type: String,
    val url: String
)