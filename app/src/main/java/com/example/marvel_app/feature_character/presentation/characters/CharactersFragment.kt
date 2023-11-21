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
import com.example.marvel_app.feature_character.presentation.OrderBy

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

        setOrderBarText(getString(R.string.ordering_by_name))

        binding.discoverGridRecyclerView.adapter = adapter

        viewModel.loadFavoriteCharactersList()
        setOrderListCLickListener()
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

                if (!binding.discoverGridRecyclerView.canScrollVertically(1)) {
                    if (viewModel.isSearchBarOpen.value == false
                        && !viewModel.charactersListEnded
                        && viewModel.discoverStatusMediator.value != ListStatus.LOADING
                    ) {
                        viewModel.charactersList.value?.let { viewModel.setCharactersList(it.size) }
                        binding.discoverGridRecyclerView.scrollToPosition(adapter.itemCount - 2)

                    } else if (viewModel.isSearchBarOpen.value == true
                        && !viewModel.searchedCharactersListEnded
                        && viewModel.searchStatusMediator.value != ListStatus.LOADING
                    ) {
                        viewModel.searchedCharacters.value?.let {
                            viewModel.searchCharacters(
                                it.size,
                                marvelTopAppBar.marvelTopAppBarSearchText.text.toString()
                            )
                        }
                        binding.discoverGridRecyclerView.scrollToPosition(adapter.itemCount - 2)
                    }

                }
            }

        })
    }

    private fun setOrderBarText(typeOfOrder: String) {
        binding.listOrderBar.apply {
            if (viewModel.isSearchBarOpen.value == true) {
                listOrderTypeText.text = typeOfOrder
                listOrderAscDsc.text =
                    if(viewModel.searchOrderBy == OrderBy.NAME_ASCENDING)getString(R.string.down_arrow)
                    else getString(R.string.up_arrow)

            }else {
                listOrderTypeText.text = typeOfOrder
                listOrderAscDsc.text =  if(viewModel.discoverOrderBy == OrderBy.NAME_ASCENDING)getString(R.string.down_arrow)
                else getString(R.string.up_arrow)
            }
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

    private fun observeDiscoverStatusMediatorStatus() {
        viewModel.discoverStatusMediator.observe(viewLifecycleOwner) { discoverStatusMediator ->
            if (viewModel.isSearchBarOpen.value == true)
                return@observe

            when (discoverStatusMediator) {
                ListStatus.LOADING -> {
                    binding.listOrderBar.listOrderChangeBtn.isEnabled = false
                    statusView.visibility = View.VISIBLE
                    if (viewModel.favoriteStatus.value == ListStatus.LOADING) {
                        binding.discoverGridRecyclerView.visibility = View.GONE
                    }
                }

                ListStatus.DONE -> {
                    binding.listOrderBar.listOrderChangeBtn.isEnabled = true

                    statusView.visibility = View.GONE
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE

                }

                ListStatus.ERROR -> {}

                else -> {}
            }

        }
    }

    private fun observeSearchStatusMediatorStatus() {
        viewModel.searchStatusMediator.observe(viewLifecycleOwner) { searchStatusMediator ->
            if (viewModel.isSearchBarOpen.value == false)
                return@observe

            when (searchStatusMediator) {
                ListStatus.LOADING -> {
                    binding.listOrderBar.listOrderChangeBtn.isEnabled = false

                    showNoResultsFound(false)

                    if (viewModel.searchNewList || viewModel.favoriteStatus.value == ListStatus.LOADING) {
                        binding.discoverGridRecyclerView.visibility = View.GONE
                    }
                    binding.statusImage.visibility = View.VISIBLE
                }

                ListStatus.DONE -> {
                    binding.listOrderBar.listOrderChangeBtn.isEnabled = true

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
                else if (viewModel.searchNewList) 0
                else return@post
            binding.discoverGridRecyclerView.scrollToPosition(position)
        }
    }

    override fun setScreenStatus(isSearchBarOpen: Boolean) {
        if (isSearchBarOpen) {
            binding.statusImage.visibility = View.GONE
            when (viewModel.searchStatusMediator.value) {
                ListStatus.LOADING -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.discoverGridRecyclerView.visibility = View.GONE

                }

                ListStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE
                }

                else -> {}
            }
        } else {
            binding.statusImage.visibility = View.GONE
            when (viewModel.discoverStatusMediator.value) {
                ListStatus.LOADING -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.discoverGridRecyclerView.visibility = View.GONE

                }

                ListStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
                    binding.discoverGridRecyclerView.visibility = View.VISIBLE
                }

                else -> {}

            }
        }
        setOrderListCLickListener()
        setOrderBarText(getString(R.string.ordering_by_name))
    }

    private fun setOrderListCLickListener() {
        binding.listOrderBar.listOrderChangeBtn.setOnClickListener {
            if (viewModel.isSearchBarOpen.value == true) {
                viewModel.changeOrderSearch()
                handler.post(searchRunnable)
            } else {
                viewModel.changeOrderDiscover()
                viewModel.setCharactersList(0)

            }
            setOrderBarText(getString(R.string.ordering_by_name))
        }
    }
}