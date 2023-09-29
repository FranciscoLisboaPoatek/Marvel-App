package com.example.marvel_app.feature_character.presentation.characters

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ComponentMarvelTopAppBarBinding
import com.example.marvel_app.databinding.FragmentDiscoverBinding
import com.example.marvel_app.feature_character.presentation.BaseFragment
import com.example.marvel_app.feature_character.presentation.CreateSpannable
import com.google.android.material.appbar.MaterialToolbar

class CharactersFragment : BaseFragment<FragmentDiscoverBinding>() {

    private val charactersViewModel: CharactersViewModel by viewModels()
    private val adapter = CharactersListAdapter()
    private lateinit var marvelTopAppBar: ComponentMarvelTopAppBarBinding
    private lateinit var marvelLogo: ImageView
    private lateinit var topBar: MaterialToolbar
    private lateinit var searchBar: EditText

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(inflater)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        marvelTopAppBar= binding.marvelTopAppBar

        marvelLogo = marvelTopAppBar.marvelTopAppBarLogo
        topBar = marvelTopAppBar.marvelTopAppBarToolbar
        searchBar = marvelTopAppBar.marvelTopAppBarSearchText

        binding.apply {
            listOrderBar.apply {
                listOrderTypeText.text = getString(R.string.ordering_by_name)
                listOrderAscDsc.text = getString(R.string.down_arrow)
            }

            discoverGridRecyclerView.adapter = adapter
        }

        topBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_item -> {
                    charactersViewModel.switchIsSearchBarOpen()
                    true
                }

                else -> false
            }
        }

        adapter.submitList(charactersViewModel.charactersList.value)

        charactersViewModel.charactersList.observe(viewLifecycleOwner) { characterList ->
            adapter.submitList(characterList)
        }

        charactersViewModel.isSearchBarOpen.observe(viewLifecycleOwner) { isOpen ->
            setUpSearchBar(isOpen)
        }
    }


    private fun setUpSearchBar(isOpen: Boolean) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val menuItem = topBar.menu.findItem(R.id.search_item)

        if (isOpen) {
            menuItem.icon =
                getDrawable(this.requireContext(), R.drawable.close_24px)

            marvelLogo.visibility = View.GONE
            searchBar.visibility = View.VISIBLE
            searchBar.requestFocus()
            imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
        } else {
            menuItem.icon =
                getDrawable(this.requireContext(), R.drawable.ic_search)

            marvelLogo.visibility = View.VISIBLE
            searchBar.visibility = View.GONE
            imm.hideSoftInputFromWindow(searchBar.windowToken, 0)
        }
    }
}