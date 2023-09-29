package com.example.marvel_app.feature_character.domain.models
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Character(
    val id: String,
    val imgUrl: String,
    val name: String,
    val description: String
) : Parcelable