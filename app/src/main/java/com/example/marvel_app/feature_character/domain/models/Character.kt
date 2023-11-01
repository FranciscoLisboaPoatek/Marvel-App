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
) : Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Character
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}