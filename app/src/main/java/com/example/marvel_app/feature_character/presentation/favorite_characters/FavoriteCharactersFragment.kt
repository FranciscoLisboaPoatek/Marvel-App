package com.example.marvel_app.feature_character.presentation.favorite_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.marvel_app.databinding.FragmentFavoritesBinding
import com.example.marvel_app.feature_character.presentation.BaseFragment

class FavoriteCharactersFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val favoriteCharactersViewModel: FavoriteCharactersViewModel by viewModels()
    override fun onCreateBinding(inflater: LayoutInflater): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {

        val adapter = FavoriteCharactersListAdapter()

        binding.favoritesRecyclerView.adapter = adapter

        adapter.submitList(favoriteCharactersViewModel.favoriteCharactersList.value)

        favoriteCharactersViewModel.favoriteCharactersList.observe(viewLifecycleOwner) { favoriteCharactersList ->
            adapter.submitList(favoriteCharactersList)
        }
    }
}