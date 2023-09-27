package com.example.marvel_app.feature_character.presentation.favorite_characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel_app.feature_character.domain.models.Character

class FavoriteCharactersViewModel: ViewModel() {

    private val _favoriteCharactersList = MutableLiveData<List<Character>>()
    val favoriteCharactersList : MutableLiveData<List<Character>> = _favoriteCharactersList
}