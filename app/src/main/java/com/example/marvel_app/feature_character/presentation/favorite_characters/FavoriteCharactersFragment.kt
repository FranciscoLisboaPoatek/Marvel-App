package com.example.marvel_app.feature_character.presentation.favorite_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.marvel_app.R
import com.example.marvel_app.databinding.FragmentFavoritesBinding
import com.example.marvel_app.feature_character.presentation.BaseFragment
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarInflater

class FavoriteCharactersFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val favoriteCharactersViewModel: FavoriteCharactersViewModel by activityViewModels()
    private lateinit var adapter: FavoriteCharactersListAdapter

    override fun onCreateBinding(inflater: LayoutInflater): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        MarvelTopAppBarInflater(this, favoriteCharactersViewModel, binding.marvelTopAppBar)
            .setupMarvelAppTopBar()

        adapter = FavoriteCharactersListAdapter(FavoriteCharacterClickListener { character ->
            val action =
                FavoriteCharactersFragmentDirections.actionFavoriteCharactersFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
        })

        binding.apply {
            listOrderBar.apply {
                listOrderTypeText.text = getString(R.string.ordering_by_name)
                listOrderAscDsc.text = getString(R.string.down_arrow)
            }
            favoritesRecyclerView.adapter = adapter
        }

        adapter.submitList(favoriteCharactersViewModel.favoriteCharactersList.value)

        favoriteCharactersViewModel.favoriteCharactersList.observe(viewLifecycleOwner) { favoriteCharactersList ->
            adapter.submitList(favoriteCharactersList)
        }
    }
}