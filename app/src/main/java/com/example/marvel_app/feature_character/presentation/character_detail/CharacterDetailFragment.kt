package com.example.marvel_app.feature_character.presentation.character_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.marvel_app.R
import com.example.marvel_app.databinding.FragmentCharacterDetailBinding
import com.example.marvel_app.feature_character.presentation.BaseFragment

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {

    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    val args: CharacterDetailFragmentArgs by navArgs()
    override fun onCreateBinding(inflater: LayoutInflater): FragmentCharacterDetailBinding {
        return FragmentCharacterDetailBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        characterDetailViewModel.onCharacterClicked(args.character)
        binding.character = characterDetailViewModel.character.value

        val navHostFragment = findNavController()
        val toolbar = binding.toolbar
        val appBarConfiguration = AppBarConfiguration(navHostFragment.graph)
        toolbar.setupWithNavController(navHostFragment, appBarConfiguration)
        toolbar.setNavigationIcon(R.drawable.arrow_back_24px)

    }
}