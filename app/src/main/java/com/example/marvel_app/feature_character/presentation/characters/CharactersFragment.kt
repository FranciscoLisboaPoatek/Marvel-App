package com.example.marvel_app.feature_character.presentation.characters

import android.os.Bundle
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

        observeCharactersList()
        observeDiscoverStatusMediatorStatus()
        observeSearchStatusMediatorStatus()
        viewModel.isSearchBarOpen.value?.let { setScreenStatus(it) }
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

    private fun observeDiscoverStatusMediatorStatus(){
        viewModel.discoverStatusMediator.observe(viewLifecycleOwner){discoverStatusMediator ->
            if (viewModel.isSearchBarOpen.value == true)
                return@observe

            when (discoverStatusMediator) {
                ListStatus.LOADING -> {
                    statusView.visibility = View.VISIBLE
                    if (viewModel.favoriteStatus.value == ListStatus.LOADING) {
                        binding.discoverGridRecyclerView.visibility = View.GONE
                    }
                }

                ListStatus.DONE -> {
                        statusView.visibility = View.GONE
                        binding.discoverGridRecyclerView.visibility = View.VISIBLE

                }

                ListStatus.ERROR -> {}

                else -> {}
            }

        }
    }

    private fun observeSearchStatusMediatorStatus(){
        viewModel.searchStatusMediator.observe(viewLifecycleOwner){searchStatusMediator ->
            if (viewModel.isSearchBarOpen.value == false)
                return@observe

            when (searchStatusMediator) {
                ListStatus.LOADING -> {
                    showNoResultsFound(false)
                    if(viewModel.searchTextChanged || viewModel.favoriteStatus.value == ListStatus.LOADING){
                        binding.discoverGridRecyclerView.visibility = View.GONE
                    }
                    binding.statusImage.visibility = View.VISIBLE
                }

                ListStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
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
            when(viewModel.searchStatusMediator.value){
                ListStatus.LOADING->{
                   binding.statusImage.visibility = View.VISIBLE
                    binding.discoverGridRecyclerView.visibility = View.GONE

                }
                ListStatus.DONE->{
                    binding.statusImage.visibility = View.GONE
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE
                }
                else -> {}
            }
        } else {
            binding.statusImage.visibility =  View.GONE
            when(viewModel.discoverStatusMediator.value){
                ListStatus.LOADING->{
                    binding.statusImage.visibility =  View.VISIBLE
                    binding.discoverGridRecyclerView.visibility = View.GONE

                }
                ListStatus.DONE->{
                    binding.statusImage.visibility =  View.GONE
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE
                }
                else -> {}

            }
        }
    }
}