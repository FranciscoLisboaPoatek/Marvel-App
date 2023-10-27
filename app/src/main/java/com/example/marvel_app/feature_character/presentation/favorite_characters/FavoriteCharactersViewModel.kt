package com.example.marvel_app.feature_character.presentation.favorite_characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.use_cases.CharactersListUseCase
import com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar.MarvelTopAppBarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCharactersViewModel @Inject constructor(
    private val charactersListUseCase: CharactersListUseCase
) : MarvelTopAppBarViewModel()  {

    private val _favoriteCharactersList = MutableLiveData<List<Character>>()
    override val charactersList: LiveData<List<Character>> = _favoriteCharactersList

    init {
        setFavoriteCharactersList()

        _searchedCharacters.value = listOf(
            Character("6", "", "","Black Widow", "",true),
            Character("2", "", "","Hulk", "",true),
        )
    }

    fun setFavoriteCharactersList(){
        viewModelScope.launch {
            _favoriteCharactersList.value = charactersListUseCase.favoriteCharactersList()
        }

    }
    override fun searchCharacters(offset: Int, name: String) {

    }

}