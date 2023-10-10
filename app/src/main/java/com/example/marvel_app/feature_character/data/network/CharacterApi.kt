package com.example.marvel_app.feature_character.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("/v1/public/characters?limit=50&")
    suspend fun getCharacters(
        @Query("offset") offset:Int
    ): ApiResponse
}