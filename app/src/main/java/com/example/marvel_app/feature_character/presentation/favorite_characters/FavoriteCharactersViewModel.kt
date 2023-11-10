package com.example.marvel_app.feature_character.presentation.favorite_characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.use_cases.FavoriteCharactersListUseCase
import com.example.marvel_app.feature_character.presentation.ListStatus
import com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar.MarvelTopAppBarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCharactersViewModel @Inject constructor(
    private val favoriteCharactersListUseCase: FavoriteCharactersListUseCase
) : MarvelTopAppBarViewModel() {

    private val _status = MutableLiveData<ListStatus>()
    val status: LiveData<ListStatus> = _status

    private val _searchStatus = MutableLiveData<ListStatus>()
    val searchStatus: LiveData<ListStatus> = _searchStatus

    private val _favoriteCharactersList = MutableLiveData<List<Character>>()
    override val charactersList: LiveData<List<Character>> = _favoriteCharactersList

    private var _characterListPosition: Int = 0
    val characterListPosition get() = _characterListPosition

    init {
        setFavoriteCharactersList()

        _searchedCharacters.value = listOf()
    }

    fun setFavoriteCharactersList() {
        _status.value = ListStatus.LOADING
        try {
            viewModelScope.launch {
                _favoriteCharactersList.value =
                    favoriteCharactersListUseCase.favoriteCharactersList()
                _status.value = ListStatus.DONE
            }
        } catch (ex: Exception) {
            _status.value = ListStatus.ERROR
        }
    }

    override fun searchCharacters(offset: Int, name: String) {
        _searchStatus.value = ListStatus.LOADING
        try {
            viewModelScope.launch {
                _searchedCharacters.value =
                    favoriteCharactersListUseCase.searchFavoriteCharacters(name)
                _foundSearchResults.value =
                    searchedCharacters.value?.isNotEmpty() == true
                _searchStatus.value = ListStatus.DONE
            }
        } catch (ex: Exception) {
            _searchStatus.value = ListStatus.ERROR
        }
    }

    fun setCharactersListPosition(position: Int) {
        _characterListPosition = position
    }

}