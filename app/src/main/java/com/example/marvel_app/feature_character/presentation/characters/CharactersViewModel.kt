package com.example.marvel_app.feature_character.presentation.characters

import androidx.lifecycle.MutableLiveData
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarViewModel

class CharactersViewModel : MarvelTopAppBarViewModel() {

    private val _charactersList = MutableLiveData<List<Character>>()
    val charactersList: MutableLiveData<List<Character>> = _charactersList

    init {
        _charactersList.value =
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