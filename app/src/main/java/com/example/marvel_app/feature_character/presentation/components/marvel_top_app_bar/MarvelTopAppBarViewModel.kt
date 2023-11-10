package com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel_app.feature_character.domain.models.Character

abstract class MarvelTopAppBarViewModel : ViewModel() {
    abstract val charactersList: LiveData<List<Character>>

    private val _isSearchBarOpen = MutableLiveData<Boolean>(false)
    val isSearchBarOpen: LiveData<Boolean> = _isSearchBarOpen

    protected val _searchedCharacters = MutableLiveData<List<Character>>()
    val searchedCharacters: LiveData<List<Character>> = _searchedCharacters

    protected var _searchedCharactersListEnded: Boolean = false
    val searchedCharactersListEnded get() = _searchedCharactersListEnded

    protected val _foundSearchResults = MutableLiveData<Boolean>()
    val foundSearchResults: LiveData<Boolean> = _foundSearchResults

    val searchText = MutableLiveData<String>()


    fun switchIsSearchBarOpen() {
        _isSearchBarOpen.value = !(_isSearchBarOpen.value ?: true)
    }

    abstract fun searchCharacters(offset: Int, name: String)
}