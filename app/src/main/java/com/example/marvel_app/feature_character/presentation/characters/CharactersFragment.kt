package com.example.marvel_app.feature_character.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.databinding.ComponentMarvelTopAppBarBinding
import com.example.marvel_app.databinding.FragmentDiscoverBinding
import com.example.marvel_app.feature_character.presentation.ListStatus
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarBaseFragment
import com.example.marvel_app.R

class CharactersFragment :
    MarvelTopAppBarBaseFragment<FragmentDiscoverBinding, CharactersListAdapter.CharacterViewHolder>() {

    override lateinit var marvelTopAppBar: ComponentMarvelTopAppBarBinding
    override val viewModel: CharactersViewModel by activityViewModels()
    override val adapter by lazy { createCharacterListAdapter() }
    private lateinit var statusView: ProgressBar

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        statusView = binding.statusImage
        marvelTopAppBar = binding.marvelTopAppBar
        setRecyclerViewScrollListener()
        setupMarvelAppTopBar()

        setOrderBarTex(getString(R.string.ordering_by_name), getString(R.string.down_arrow))

        binding.discoverGridRecyclerView.adapter = adapter

        viewModel.loadFavoriteCharactersList()
        observeFavoriteStatus()
        observeSearchStatus()
        observeStatus()
    }

    private fun setRecyclerViewScrollListener() {
        binding.discoverGridRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!binding.discoverGridRecyclerView.canScrollVertically(1)
                    && viewModel.status.value != ListStatus.LOADING
                ) {
                    if (viewModel.isSearchBarOpen.value == false && !viewModel.charactersListEnded) {
                        viewModel.setCharactersList(adapter.itemCount)
                        binding.discoverGridRecyclerView.scrollToPosition(adapter.itemCount - 2)
                    } else if (viewModel.isSearchBarOpen.value == true && !viewModel.searchedCharactersListEnded) {
                        viewModel.searchCharacters(
                            adapter.itemCount,
                            marvelTopAppBar.marvelTopAppBarSearchText.text.toString()
                        )
                        binding.discoverGridRecyclerView.scrollToPosition(adapter.itemCount - 2)
                    }

                }
            }

        })
    }

    private fun setOrderBarTex(typeOfOrder: String, ascendingOrDescending: String) {
        binding.listOrderBar.apply {
            listOrderTypeText.text = typeOfOrder
            listOrderAscDsc.text = ascendingOrDescending
        }
    }

    private fun createCharacterListAdapter(): CharactersListAdapter {
        return CharactersListAdapter(CharacterClickListener { character ->
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
        },
            CharacterClickListener { character ->
                viewModel.favoriteCharacter(character)
            },
            { character ->
                viewModel.isItemFavorited(character)
            })
    }

    private fun observeCharactersList() {
        viewModel.charactersList.observe(viewLifecycleOwner) { characterList ->
            if (viewModel.isSearchBarOpen.value == false) {

                adapter.submitList(characterList)
                binding.discoverGridRecyclerView.visibility =
                    if (characterList.isEmpty()) View.GONE else View.VISIBLE

            }
        }
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ListStatus.LOADING -> {
                    Log.w("charactes", "charactes loading")
                    statusView.visibility = View.VISIBLE
                    if (viewModel.isSearchBarOpen.value == true) {
                        binding.discoverGridRecyclerView.visibility = View.GONE
                    }
                    //enableSearch(false)
                }

                ListStatus.DONE -> {
                    Log.w("charactes", "charactes done")
                    if (viewModel.favoriteStatus.value != ListStatus.LOADING) {
                        statusView.visibility = View.GONE
                        binding.discoverGridRecyclerView.visibility = View.VISIBLE
                    }
                    //enableSearch(true)
                }

                ListStatus.ERROR -> {}

                else -> {}
            }

        }
    }

    private fun observeFavoriteStatus() {
        viewModel.favoriteStatus.observe(viewLifecycleOwner) { favoriteStatus ->
            when (favoriteStatus) {
                ListStatus.LOADING -> {
                    Log.w("charactes", "favorites loading")
                    binding.discoverGridRecyclerView.visibility = View.GONE
                    statusView.visibility = View.VISIBLE
                    enableSearch(false)
                }

                ListStatus.DONE -> {
                    Log.w("charactes", "favorites done")
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE
                    observeCharactersList()
                    if (viewModel.status.value != ListStatus.LOADING) {
                        statusView.visibility = View.GONE
                    }
                    enableSearch(true)

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
                    binding.discoverGridRecyclerView.visibility = View.GONE
                }

                ListStatus.DONE -> {
                    binding.searchStatusImage.visibility = View.GONE
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE
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
        val layoutManager = binding.discoverGridRecyclerView.layoutManager as GridLayoutManager
        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
        viewModel.setCharactersListPosition(position)
    }

    override fun adjustListPosition() {
        binding.discoverGridRecyclerView.post {
            val position =
                if (viewModel.isSearchBarOpen.value == false) viewModel.characterListPosition
                else 0
            binding.discoverGridRecyclerView.scrollToPosition(position)
        }
    }

    override fun setScreenStatus(isSearchBarOpen: Boolean) {
        if (isSearchBarOpen) {
            binding.statusImage.visibility =  View.GONE
            when(viewModel.searchStatus.value){
                ListStatus.LOADING->{
                    binding.searchStatusImage.visibility = View.VISIBLE
                }
                ListStatus.DONE->{
                    binding.searchStatusImage.visibility = View.GONE
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE
                }
                else -> {}
            }
        } else {
            binding.searchStatusImage.visibility =  View.GONE
            if (viewModel.status.value == ListStatus.LOADING || viewModel.favoriteStatus.value == ListStatus.LOADING){
                binding.statusImage.visibility =  View.VISIBLE
            }else{
                binding.statusImage.visibility =  View.GONE
                binding.discoverGridRecyclerView.visibility = View.VISIBLE

            }
        }
    }
}