package com.example.marvel_app.feature_character.presentation.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.use_cases.FavoriteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val favoriteCharacterUseCase: FavoriteCharacterUseCase
): ViewModel() {
    private val _character=  MutableLiveData<Character>()
    val character: LiveData<Character> = _character

    init {
        val navigationCharacter: Character? = savedStateHandle["character"]
        if (navigationCharacter != null) {
            onCharacterClicked(navigationCharacter)
        }
    }

    fun favoriteCharacter(){
        viewModelScope.launch {
            character.value?.let { favoriteCharacterUseCase.execute(it) }
        }
    }

    fun onCharacterClicked(character: Character){
        _character.value = character
    }
}