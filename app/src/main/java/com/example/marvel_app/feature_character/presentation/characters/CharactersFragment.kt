package com.example.marvel_app.feature_character.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.marvel_app.R
import com.example.marvel_app.databinding.FragmentDiscoverBinding
import com.example.marvel_app.feature_character.presentation.BaseFragment
import com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar.MarvelTopAppBarHandler

class CharactersFragment : BaseFragment<FragmentDiscoverBinding>() {

    private val charactersViewModel: CharactersViewModel by activityViewModels()
    private lateinit var adapter: CharactersListAdapter

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        val marvelTopAppBarHandler = MarvelTopAppBarHandler(binding.marvelTopAppBar)
        marvelTopAppBarHandler.setupMarvelAppTopBar(charactersViewModel)

        adapter = CharactersListAdapter(CharacterClickListener { character ->
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
        })

        binding.apply {
            listOrderBar.apply {
                listOrderTypeText.text = getString(R.string.ordering_by_name)
                listOrderAscDsc.text = getString(R.string.down_arrow)
            }

            discoverGridRecyclerView.adapter = adapter
        }

        charactersViewModel.charactersList.observe(viewLifecycleOwner) { characterList ->
            adapter.submitList(characterList)
            binding.discoverGridRecyclerView.visibility = if (characterList.isEmpty()) View.GONE else View.VISIBLE

        }

        charactersViewModel.searchedCharacters.observe(viewLifecycleOwner){searchedCharactersList ->
            if (charactersViewModel.isSearchBarOpen.value == true) {
                adapter.submitList(searchedCharactersList)

            }
        }

        charactersViewModel.isSearchBarOpen.observe(viewLifecycleOwner) { isSearchBarOpen ->
            marvelTopAppBarHandler.setUpSearchBar(isSearchBarOpen, this.requireContext())
            if (isSearchBarOpen) {
                adapter.submitList(charactersViewModel.searchedCharacters.value)
            } else {
                adapter.submitList(charactersViewModel.charactersList.value)
            }
        }
    }
}