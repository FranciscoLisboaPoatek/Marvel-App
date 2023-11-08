package com.example.marvel_app.feature_character.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersListUseCase: CharactersListUseCase,
    private val favoriteCharacterUseCase: FavoriteCharacterUseCase,
    private val favoriteCharactersListUseCase: FavoriteCharactersListUseCase
) : MarvelTopAppBarViewModel() {

    private val _status = MutableLiveData<ListStatus>()
    val status: LiveData<ListStatus> = _status

    private val _favoriteStatus = MutableLiveData<ListStatus>()
    val favoriteStatus: LiveData<ListStatus> = _favoriteStatus

    private val _searchStatus = MutableLiveData<ListStatus>()
    val searchStatus: LiveData<ListStatus> = _searchStatus

    private val _charactersList = MutableLiveData<List<Character>>(listOf())
    override val charactersList: LiveData<List<Character>> = _charactersList

    private val _favoriteCharactersList = MutableLiveData<List<Character>>()

    private var _charactersListEnded: Boolean = false
    val charactersListEnded get() = _charactersListEnded

    private var _characterListPosition: Int = 0
    val characterListPosition get() = _characterListPosition

    init {
        setCharactersList(0)
        //setFavoriteCharactersList()
        _searchedCharacters.value = listOf()
    }

    val searchStatusMediator: LiveData<ListStatus> = MediatorLiveData<ListStatus>().apply {
        fun updateLoading(){
            if(searchStatus.value == ListStatus.LOADING || favoriteStatus.value == ListStatus.LOADING){
                value = ListStatus.LOADING
            }else if (searchStatus.value == ListStatus.DONE && favoriteStatus.value == ListStatus.DONE) {
                value = ListStatus.DONE
            }
        }
        addSource(searchStatus){updateLoading()}
        addSource(favoriteStatus){updateLoading()}
    }

    val discoverStatusMediator: LiveData<ListStatus> = MediatorLiveData<ListStatus>().apply {
        fun updateLoading(){
            if(status.value == ListStatus.LOADING || favoriteStatus.value == ListStatus.LOADING){
                value = ListStatus.LOADING
            }else if (status.value == ListStatus.DONE && favoriteStatus.value == ListStatus.DONE) {
                value = ListStatus.DONE
            }
        }
        addSource(status){updateLoading()}
        addSource(favoriteStatus){updateLoading()}
    }

    fun setCharactersList(offset: Int) {
        _status.value = ListStatus.LOADING
        viewModelScope.launch {
            try {
                delay(10000)
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
        _searchStatus.value = ListStatus.LOADING
        viewModelScope.launch {
            try {
                delay(5000)
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
                _searchStatus.value = ListStatus.DONE

            } catch (ex: Exception) {
                _searchStatus.value = ListStatus.ERROR
            }
        }
    }

    fun setCharactersListPosition(position: Int) {
        _characterListPosition = position
    }

    fun favoriteCharacter(character: Character) {
        viewModelScope.launch {
            favoriteCharacterUseCase.execute(character)
            setFavoriteCharactersList()
        }
        character.isFavorited = !(character.isFavorited)
    }

    suspend fun setFavoriteCharactersList() {
        delay(5000)
        _favoriteCharactersList.value = favoriteCharactersListUseCase.favoriteCharactersList()
    }

    fun isItemFavorited(character: Character) {
        character.isFavorited = _favoriteCharactersList.value?.contains(character) ?: false
    }

    fun loadFavoriteCharactersList() {
        _favoriteStatus.value = ListStatus.LOADING
        viewModelScope.launch {
            setFavoriteCharactersList()
            _favoriteStatus.value = ListStatus.DONE
        }
    }
}