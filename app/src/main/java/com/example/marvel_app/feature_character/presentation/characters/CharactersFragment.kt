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
    private lateinit var discoverRecyclerView: RecyclerView
    private lateinit var statusView: ProgressBar

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        discoverRecyclerView = binding.discoverGridRecyclerView
        statusView = binding.statusImage
        marvelTopAppBar = binding.marvelTopAppBar
        marvelTopAppBar.viewModel = viewModel
        setRecyclerViewScrollListener()
        setupMarvelAppTopBar()

        setOrderBarTex(getString(R.string.ordering_by_name), getString(R.string.down_arrow))

        discoverRecyclerView.adapter = adapter

        observeCharactersList()
        observeStatus()
    }

    private fun setRecyclerViewScrollListener() {
        discoverRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!discoverRecyclerView.canScrollVertically(1)
                    && viewModel.status.value != ListStatus.LOADING
                ) {
                    if (viewModel.isSearchBarOpen.value == false && !viewModel.charactersListEnded) {
                        viewModel.setCharactersList(adapter.itemCount)
                        discoverRecyclerView.scrollToPosition(adapter.itemCount - 2)
                    } else if (viewModel.isSearchBarOpen.value == true && !viewModel.searchedCharactersListEnded) {
                        viewModel.searchCharacters(
                            adapter.itemCount,
                            marvelTopAppBar.marvelTopAppBarSearchText.text.toString()
                        )
                        discoverRecyclerView.scrollToPosition(adapter.itemCount - 2)
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
        })
    }

    private fun observeCharactersList() {
        viewModel.charactersList.observe(viewLifecycleOwner) { characterList ->
            if (viewModel.isSearchBarOpen.value == false) {
                adapter.submitList(characterList)
                discoverRecyclerView.visibility =
                    if (characterList.isEmpty()) View.GONE else View.VISIBLE

            }
        }
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ListStatus.LOADING -> {
                    statusView.visibility = View.VISIBLE
                }

                ListStatus.DONE -> {
                    statusView.visibility = View.GONE
                }

                ListStatus.ERROR -> {

                }

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
}