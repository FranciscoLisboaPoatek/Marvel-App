package com.example.marvel_app.feature_character.presentation.favorite_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvel_app.databinding.ComponentMarvelTopAppBarBinding
import com.example.marvel_app.databinding.FragmentFavoritesBinding
import com.example.marvel_app.feature_character.presentation.ListStatus
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

        binding.favoritesRecyclerView.adapter = adapter

        observeFavoriteCharactersList()
        observeStatus()
        observeSearchStatus()
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
                enableSearch(favoriteCharactersList.isNotEmpty())
                if (favoriteCharactersList.isEmpty())
                    binding.noFavoritesMessage.visibility = View.VISIBLE

            }
        }
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ListStatus.LOADING -> {
                    binding.noFavoritesMessage.visibility = View.GONE
                    binding.statusImage.visibility = View.VISIBLE
                    binding.favoritesRecyclerView.visibility = View.GONE

                }

                ListStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
                    binding.favoritesRecyclerView.visibility = View.VISIBLE

                }

                ListStatus.ERROR -> {}

                else -> {}
            }
        }
    }

    private fun observeSearchStatus() {
        viewModel.searchStatus.observe(viewLifecycleOwner) { status ->
            if (viewModel.isSearchBarOpen.value == false) return@observe
            when (status) {
                ListStatus.LOADING -> {
                    binding.searchStatusImage.visibility = View.VISIBLE
                    showNoResultsFound(false)
                    binding.favoritesRecyclerView.visibility = View.GONE
                }

                ListStatus.DONE -> {
                    binding.searchStatusImage.visibility = View.GONE
                    binding.favoritesRecyclerView.visibility = View.VISIBLE
                }

                ListStatus.ERROR -> {}

                else -> {}
            }
        }
    }

    override fun showNoResultsFound(notFound: Boolean) {
        binding.noSearchResultMessage.visibility =
            if (notFound) View.VISIBLE else View.GONE
    }

    override fun saveListPosition() {
        val layoutManager = binding.favoritesRecyclerView.layoutManager as LinearLayoutManager
        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
        viewModel.setCharactersListPosition(position)
    }

    override fun adjustListPosition() {
        binding.favoritesRecyclerView.post {
            val position =
                if (viewModel.isSearchBarOpen.value == false) viewModel.characterListPosition
                else 0
            binding.favoritesRecyclerView.scrollToPosition(position)
        }
    }

    override fun setScreenStatus(isSearchBarOpen: Boolean) {
        if (isSearchBarOpen){
            binding.statusImage.visibility =  View.GONE
            when(viewModel.searchStatus.value){
                ListStatus.LOADING->{
                    binding.searchStatusImage.visibility = View.VISIBLE
                }
                ListStatus.DONE->{
                    binding.searchStatusImage.visibility = View.GONE
                    binding.favoritesRecyclerView.visibility = View.VISIBLE
                }
                else -> {}
            }
        }else{
            binding.searchStatusImage.visibility = View.GONE
            when(viewModel.status.value){
                ListStatus.LOADING->{
                    binding.statusImage.visibility =  View.VISIBLE
                }
                ListStatus.DONE->{
                    binding.statusImage.visibility =  View.GONE
                    binding.favoritesRecyclerView.visibility = View.VISIBLE

                }
                else -> {}
            }
        }
    }

}