package com.example.marvel_app.feature_character.presentation.character_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.marvel_app.feature_character.domain.models.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _character=  MutableLiveData<Character>()
    val character: MutableLiveData<Character> = _character

    init {
        val navigationCharacter: Character? = savedStateHandle["character"]
        if (navigationCharacter != null) {
            onCharacterClicked(navigationCharacter)
        }
    }


    fun onCharacterClicked(character: Character){
        _character.value = character
    }
}