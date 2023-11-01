package com.example.marvel_app.feature_character.presentation.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.domain.use_cases.CharactersListUseCase
import com.example.marvel_app.feature_character.domain.use_cases.FavoriteCharacterUseCase
import com.example.marvel_app.feature_character.domain.use_cases.FavoriteCharactersListUseCase
import com.example.marvel_app.feature_character.presentation.ListStatus
import com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar.MarvelTopAppBarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersListUseCase: CharactersListUseCase,
    private val favoriteCharacterUseCase: FavoriteCharacterUseCase,
    private val favoriteCharactersListUseCase: FavoriteCharactersListUseCase
) : MarvelTopAppBarViewModel() {

    private val _status = MutableLiveData<ListStatus>()
    val status: LiveData<ListStatus> = _status

    private val _charactersList = MutableLiveData<List<Character>>(listOf())
    override val charactersList: LiveData<List<Character>> = _charactersList

    private val _favoriteCharactersList = MutableLiveData<List<Character>>()

    private var _charactersListEnded: Boolean = false
    val charactersListEnded get() = _charactersListEnded

    private var _characterListPosition: Int = 0
    val characterListPosition get() = _characterListPosition

    init {
        setCharactersList(0)
        setFavoriteCharactersList()
        _searchedCharacters.value = listOf()
    }

    fun setCharactersList(offset: Int) {
        viewModelScope.launch {
            _status.value = ListStatus.LOADING
            try {
                val characterListResponse =
                    charactersListUseCase.discoverCharactersList(offset, null)

                _charactersListEnded = characterListResponse.listEnded
                _charactersList.value =
                    _charactersList.value?.plus(characterListResponse.charactersList)
                _status.value = ListStatus.DONE
            } catch (ex: Exception) {
                _status.value = ListStatus.ERROR
            }
        }
    }

    override fun searchCharacters(offset: Int, name: String) {
        viewModelScope.launch {
            _status.value = ListStatus.LOADING
            try {
                val characterListResponse =
                    charactersListUseCase.discoverCharactersList(offset, name)

                _searchedCharactersListEnded = characterListResponse.listEnded
                if (offset == 0) {
                    _searchedCharacters.value = characterListResponse.charactersList
                    _foundSearchResults.value = characterListResponse.charactersList.isNotEmpty()

                } else {
                    _searchedCharacters.value =
                        _searchedCharacters.value?.plus(characterListResponse.charactersList)
                }
                _status.value = ListStatus.DONE

            } catch (ex: Exception) {
                _status.value = ListStatus.ERROR
            }
        }
    }

    fun setCharactersListPosition(position: Int) {
        _characterListPosition = position
    }

    fun favoriteCharacter(character: Character) {
        viewModelScope.launch {
            favoriteCharacterUseCase.execute(character)
        }
        character.isFavorited = !(character.isFavorited)
    }

    fun setFavoriteCharactersList() {
        viewModelScope.launch {
            _favoriteCharactersList.value = favoriteCharactersListUseCase.favoriteCharactersList()
        }

    }

    fun isItemFavorited(character: Character) {
        character.isFavorited = _favoriteCharactersList.value?.contains(character) ?: false
    }

}