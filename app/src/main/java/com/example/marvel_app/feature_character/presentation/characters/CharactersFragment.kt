package com.example.marvel_app.feature_character.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ComponentMarvelTopAppBarBinding
import com.example.marvel_app.databinding.FragmentDiscoverBinding
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarBaseFragment

class CharactersFragment :
    MarvelTopAppBarBaseFragment<FragmentDiscoverBinding, CharactersListAdapter.CharacterViewHolder>() {

    override lateinit var marvelTopAppBar: ComponentMarvelTopAppBarBinding
    override val viewModel: CharactersViewModel by activityViewModels()
    override val adapter by lazy { createCharacterListAdapter() }


    override fun onCreateBinding(inflater: LayoutInflater): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        setRecyclerViewScrollListener()
        marvelTopAppBar = binding.marvelTopAppBar
        setupMarvelAppTopBar()

        setOrderBarTex(getString(R.string.ordering_by_name), getString(R.string.down_arrow))

        binding.discoverGridRecyclerView.adapter = adapter

        observeCharactersList()
    }

    private fun setRecyclerViewScrollListener() {
        binding.discoverGridRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.discoverGridRecyclerView.canScrollVertically(1)) {
                    viewModel.setCharactersList(adapter.itemCount)
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
                binding.discoverGridRecyclerView.visibility =
                    if (characterList.isEmpty()) View.GONE else View.VISIBLE

            }
        }
    }
}