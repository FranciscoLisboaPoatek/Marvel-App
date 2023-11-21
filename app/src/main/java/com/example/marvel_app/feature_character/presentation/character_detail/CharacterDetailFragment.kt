package com.example.marvel_app.feature_character.presentation.character_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.marvel_app.R
import com.example.marvel_app.databinding.FragmentCharacterDetailBinding
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.presentation.BaseFragment
import com.example.marvel_app.feature_character.presentation.ImageType
import com.example.marvel_app.feature_character.presentation.ListStatus
import com.example.marvel_app.feature_character.presentation.bindImage
import com.example.marvel_app.feature_character.presentation.makeImageUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {

    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    private lateinit var favoriteMenuItem: MenuItem

    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.characterDetailFragment ->
                    binding.toolbar.setNavigationIcon(R.drawable.arrow_back_24px)
            }
        }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentCharacterDetailBinding {
        return FragmentCharacterDetailBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        binding.character = characterDetailViewModel.character.value
        val character = binding.character
        val navHostFragment = findNavController()
        val toolbar = binding.toolbar
        favoriteMenuItem = toolbar.menu.findItem(R.id.favorite_item)

        if (character != null) {

            bindImage(
                binding.characterDetailImageView,
                makeImageUrl(
                    character.imgPath,
                    character.imgExtension,
                    ImageType.DETAIL
                )
            )
            characterDetailViewModel.isCharacterFavorited()
            setCharacterFavoriteIcon(character)

            favoriteMenuItem.setOnMenuItemClickListener {
                characterDetailViewModel.favoriteCharacter()
                setCharacterFavoriteIcon(character)
                true
            }
        }
        val appBarConfiguration = AppBarConfiguration(navHostFragment.graph)
        toolbar.setupWithNavController(navHostFragment, appBarConfiguration)
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_24px)
        observeFavoriteStatus()
        findNavController().addOnDestinationChangedListener(destinationChangedListener)
    }

    private fun observeFavoriteStatus() {
        characterDetailViewModel.favoriteStatus.observe(viewLifecycleOwner) { favoriteStatus ->
            when (favoriteStatus) {
                ListStatus.LOADING -> {
                    enableFavorite(false)
                }

                ListStatus.DONE -> {
                    enableFavorite(true)
                    characterDetailViewModel.character.value?.let { setCharacterFavoriteIcon(it) }

                }

                ListStatus.ERROR -> {}
                else -> {}
            }

        }
    }


    private fun setCharacterFavoriteIcon(character: Character) {
        if (character.isFavorited) {
            favoriteMenuItem.icon =
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_star_filled)
        } else {
            favoriteMenuItem.icon =
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_star)
        }
    }

    private fun enableFavorite(enable: Boolean) {
        val searchMenuItem = binding.toolbar.menu.findItem(R.id.favorite_item)

        if (enable) {
            searchMenuItem.apply {
                isEnabled = true
                icon?.alpha = 255
            }
        } else {
            searchMenuItem.apply {
                isEnabled = false
                icon?.alpha = 130
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        findNavController().removeOnDestinationChangedListener(destinationChangedListener)

    }
}