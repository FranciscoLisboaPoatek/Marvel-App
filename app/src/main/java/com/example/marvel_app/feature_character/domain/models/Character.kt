package com.example.marvel_app.feature_character.domain.models
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Character(
    val id: String,
    val imgPath: String,
    val imgExtension: String,
    val name: String,
    val description: String,
    var isFavorited: Boolean = false
) : Parcelable