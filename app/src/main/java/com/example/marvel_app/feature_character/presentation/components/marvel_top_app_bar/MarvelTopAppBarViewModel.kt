package com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel_app.feature_character.domain.models.Character

abstract class MarvelTopAppBarViewModel: ViewModel() {
    abstract val charactersList: LiveData<List<Character>>

    private val _isSearchBarOpen = MutableLiveData<Boolean>(false)
    val isSearchBarOpen: MutableLiveData<Boolean> = _isSearchBarOpen

    protected val _searchedCharacters = MutableLiveData<List<Character>>()
    val searchedCharacters: MutableLiveData<List<Character>> = _searchedCharacters

    fun switchIsSearchBarOpen() {
        _isSearchBarOpen.value = !(_isSearchBarOpen.value?:true)
    }
}