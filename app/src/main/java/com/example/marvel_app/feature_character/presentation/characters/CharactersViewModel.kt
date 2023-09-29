package com.example.marvel_app.feature_character.presentation.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel_app.feature_character.domain.models.Character

class CharactersViewModel : ViewModel() {

    private val _charactersList = MutableLiveData<List<Character>>()
    val charactersList: MutableLiveData<List<Character>> = _charactersList

    private val _isSearchBarOpen = MutableLiveData<Boolean>(false)
    val isSearchBarOpen: MutableLiveData<Boolean> = _isSearchBarOpen

    fun switchIsSearchBarOpen():Unit {
        _isSearchBarOpen.value = !(_isSearchBarOpen.value?:true)
    }

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