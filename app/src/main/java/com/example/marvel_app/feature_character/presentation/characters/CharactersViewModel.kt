package com.example.marvel_app.feature_character.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel_app.feature_character.domain.models.Character

class CharactersViewModel : ViewModel() {

    private val _charactersList = MutableLiveData<List<Character>>()
    val charactersList: MutableLiveData<List<Character>> = _charactersList
}