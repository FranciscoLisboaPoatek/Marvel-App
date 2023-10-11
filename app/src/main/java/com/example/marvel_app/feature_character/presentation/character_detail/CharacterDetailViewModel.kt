package com.example.marvel_app.feature_character.presentation.character_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel_app.feature_character.domain.models.Character

class CharacterDetailViewModel: ViewModel() {
    private val _character=  MutableLiveData<Character>()
    val character: MutableLiveData<Character> = _character

    fun onCharacterClicked(character: Character){
        _character.value = character
    }
}