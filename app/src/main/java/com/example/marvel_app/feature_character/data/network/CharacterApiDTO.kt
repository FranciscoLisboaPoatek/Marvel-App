package com.example.marvel_app.feature_character.data.network


data class ApiResponse(
    val data: DataResult
)

data class DataResult(
    val results: List<CharacterApiDTO>,
    val total: Int,
    val count: Int
)

data class CharacterApiDTO(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
)