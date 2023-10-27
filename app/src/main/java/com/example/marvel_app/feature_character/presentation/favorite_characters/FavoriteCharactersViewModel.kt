package com.example.marvel_app.feature_character.presentation.favorite_characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.use_cases.FavoriteCharactersListUseCase
import com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar.MarvelTopAppBarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCharactersViewModel @Inject constructor(
    private val favoriteCharactersListUseCase: FavoriteCharactersListUseCase
) : MarvelTopAppBarViewModel() {

    private val _favoriteCharactersList = MutableLiveData<List<Character>>()
    override val charactersList: LiveData<List<Character>> = _favoriteCharactersList

    init {
        setFavoriteCharactersList()

        _searchedCharacters.value = listOf()
    }

    fun setFavoriteCharactersList() {
        viewModelScope.launch {
            _favoriteCharactersList.value = favoriteCharactersListUseCase.favoriteCharactersList()
        }

    }

    override fun searchCharacters(offset: Int, name: String) {
        viewModelScope.launch {
            _searchedCharacters.value = favoriteCharactersListUseCase.searchFavoriteCharacters(name)
            _foundSearchResults.value =
                searchedCharacters.value?.isNotEmpty() == true
        }
    }

}