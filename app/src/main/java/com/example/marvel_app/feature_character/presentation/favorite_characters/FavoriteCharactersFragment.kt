package com.example.marvel_app.feature_character.presentation.favorite_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ComponentMarvelTopAppBarBinding
import com.example.marvel_app.databinding.FragmentFavoritesBinding
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarBaseFragment

class FavoriteCharactersFragment :
    MarvelTopAppBarBaseFragment<FragmentFavoritesBinding, FavoriteCharactersListAdapter.FavoriteCharacterViewHolder>() {

    override lateinit var marvelTopAppBar: ComponentMarvelTopAppBarBinding
    override val viewModel: FavoriteCharactersViewModel by activityViewModels()
    override val adapter by lazy { createFavoriteCharacterListAdapter() }


    override fun onCreateBinding(inflater: LayoutInflater): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        marvelTopAppBar = binding.marvelTopAppBar
        setupMarvelAppTopBar()
        viewModel.setFavoriteCharactersList()
        setOrderBarTex(getString(R.string.ordering_by_name), getString(R.string.down_arrow))

        binding.favoritesRecyclerView.adapter = adapter

        observeFavoriteCharactersList()
    }

    private fun setOrderBarTex(typeOfOrder: String, ascendingOrDescending: String) {
        binding.listOrderBar.apply {
            listOrderTypeText.text = typeOfOrder
            listOrderAscDsc.text = ascendingOrDescending
        }
    }

    private fun createFavoriteCharacterListAdapter(): FavoriteCharactersListAdapter {
        return FavoriteCharactersListAdapter(FavoriteCharacterClickListener { character ->
            val action =
                FavoriteCharactersFragmentDirections.actionFavoriteCharactersFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
        })
    }

    private fun observeFavoriteCharactersList() {
        viewModel.charactersList.observe(viewLifecycleOwner) { favoriteCharactersList ->
            if (viewModel.isSearchBarOpen.value == false) {
                adapter.submitList(favoriteCharactersList)
                val searchMenuItem = marvelTopAppBar.marvelTopAppBarToolbar.menu.findItem(R.id.search_item)

                if (favoriteCharactersList.isEmpty()) {
                    binding.noFavoritesMessage.visibility = View.VISIBLE
                    searchMenuItem.apply {
                        isEnabled = false
                        icon?.alpha=130
                    }
                } else {
                    binding.noFavoritesMessage.visibility = View.GONE
                    searchMenuItem.apply {
                        isEnabled = true
                        icon?.alpha=255
                    }
                }
            }
        }
    }

    override fun showNoResultsFound(notFound: Boolean) {
        binding.noSearchResultMessage.visibility =
            if (notFound) View.VISIBLE else View.GONE
    }

    override fun saveListPosition() {

    }

    override fun adjustListPosition() {

    }

}