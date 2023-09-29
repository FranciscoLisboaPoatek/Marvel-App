package com.example.marvel_app.feature_character.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.marvel_app.databinding.FragmentDiscoverBinding
import com.example.marvel_app.feature_character.presentation.BaseFragment

class CharactersFragment : BaseFragment<FragmentDiscoverBinding>() {

    private val charactersViewModel: CharactersViewModel by activityViewModels()
    private lateinit var adapter: CharactersListAdapter

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        adapter = CharactersListAdapter(CharacterClickListener { character ->
            val action =
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(character)
            findNavController().navigate(action)
        })

        binding.discoverGridRecyclerView.adapter = adapter

        adapter.submitList(charactersViewModel.charactersList.value)

        charactersViewModel.charactersList.observe(viewLifecycleOwner) { characterList ->
            adapter.submitList(characterList)
        }
    }
}