package com.example.marvel_app.feature_character.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class MarvelTopAppBarViewModel: ViewModel() {

    private val _isSearchBarOpen = MutableLiveData<Boolean>(false)
    val isSearchBarOpen: MutableLiveData<Boolean> = _isSearchBarOpen

    fun switchIsSearchBarOpen() {
        _isSearchBarOpen.value = !(_isSearchBarOpen.value?:true)
    }
}