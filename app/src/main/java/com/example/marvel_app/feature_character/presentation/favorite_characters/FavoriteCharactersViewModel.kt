package com.example.marvel_app.feature_character.presentation.favorite_characters

import androidx.lifecycle.MutableLiveData
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarViewModel

class FavoriteCharactersViewModel : MarvelTopAppBarViewModel() {

    private val _favoriteCharactersList = MutableLiveData<List<Character>>()
    val favoriteCharactersList: MutableLiveData<List<Character>> = _favoriteCharactersList

    init {
        _favoriteCharactersList.value =
            mutableListOf(
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
                Character("1", "", "Iron Man", ""),
            )
    }
}