package com.example.marvel_app.feature_character.presentation.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.use_cases.FavoriteCharacterUseCase
import com.example.marvel_app.feature_character.presentation.ListStatus
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

    private val _favoriteStatus = MutableLiveData<ListStatus>()
    val favoriteStatus: LiveData<ListStatus> = _favoriteStatus

    init {
        val navigationCharacter: Character? = savedStateHandle["character"]
        if (navigationCharacter != null) {
            onCharacterClicked(navigationCharacter)
        }
    }

    fun favoriteCharacter(){
        character.value?.let {character ->
            viewModelScope.launch {
                favoriteCharacterUseCase.execute(character)
            }
            character.isFavorited = !(character.isFavorited)
        }
    }

    fun isCharacterFavorited(){
        _favoriteStatus.value = ListStatus.LOADING
        try {
            viewModelScope.launch {
                _character.value?.let {
                    it.isFavorited = favoriteCharacterUseCase.isCharacterFavorited(it)
                }
                _favoriteStatus.value = ListStatus.DONE
            }
        }catch (e:Exception){
            _favoriteStatus.value = ListStatus.ERROR
        }
    }
    fun onCharacterClicked(character: Character){
        _character.value = character
    }
}