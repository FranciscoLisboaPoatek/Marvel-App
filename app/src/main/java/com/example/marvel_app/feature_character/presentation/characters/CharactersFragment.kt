package com.example.marvel_app.feature_character.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.marvel_app.R
import com.example.marvel_app.databinding.FragmentDiscoverBinding
import com.example.marvel_app.feature_character.presentation.BaseFragment
import com.example.marvel_app.feature_character.presentation.MarvelTopAppBarInflater

class CharactersFragment : BaseFragment<FragmentDiscoverBinding>() {

    private val charactersViewModel: CharactersViewModel by viewModels()
    private val adapter = CharactersListAdapter()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        MarvelTopAppBarInflater(this,charactersViewModel,binding.marvelTopAppBar)
            .setupMarvelAppTopBar()

        binding.apply {
            listOrderBar.apply {
                listOrderTypeText.text = getString(R.string.ordering_by_name)
                listOrderAscDsc.text = getString(R.string.down_arrow)
            }

            discoverGridRecyclerView.adapter = adapter
        }

        adapter.submitList(charactersViewModel.charactersList.value)

        charactersViewModel.charactersList.observe(viewLifecycleOwner) { characterList ->
            adapter.submitList(characterList)
        }

    }

}