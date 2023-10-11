package com.example.marvel_app.feature_character.presentation

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ComponentMarvelTopAppBarBinding
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar.MarvelTopAppBarViewModel

abstract class MarvelTopAppBarBaseFragment<B : ViewBinding,VH : RecyclerView.ViewHolder>:BaseFragment<B>() {
    abstract val marvelTopAppBar: ComponentMarvelTopAppBarBinding
    abstract val viewModel: MarvelTopAppBarViewModel
    abstract val adapter:ListAdapter<Character, VH>

    fun setupMarvelAppTopBar() {
        marvelTopAppBar.marvelTopAppBarToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_item -> {
                    viewModel.switchIsSearchBarOpen()
                    true
                }

                else -> false
            }
        }
        setUpSearchBar()
        observeSearchBar()
        observeSearchedCharacterList()
    }

    private fun setUpSearchBar() {
        val isOpen = viewModel.isSearchBarOpen.value
        val searchBar: EditText = marvelTopAppBar.marvelTopAppBarSearchText
        val marvelLogo: ImageView = marvelTopAppBar.marvelTopAppBarLogo
        val context = this.requireContext()
        val imm =
            context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val menuItem = marvelTopAppBar.marvelTopAppBarToolbar.menu.findItem(R.id.search_item)

        if (isOpen == true) {
            menuItem.icon =
                ContextCompat.getDrawable(context, R.drawable.close_24px)

            marvelLogo.visibility = View.GONE
            searchBar.visibility = View.VISIBLE
            searchBar.requestFocus()
            imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT)
        } else {
            menuItem.icon =
                ContextCompat.getDrawable(context, R.drawable.ic_search)

            marvelLogo.visibility = View.VISIBLE
            searchBar.visibility = View.GONE
            imm.hideSoftInputFromWindow(searchBar.windowToken, 0)
        }
    }

    private fun observeSearchedCharacterList() {
        viewModel.searchedCharacters.observe(viewLifecycleOwner) { searchedCharactersList ->
            if (viewModel.isSearchBarOpen.value == true) {
                adapter.submitList(searchedCharactersList)
            }
        }
    }

    private fun observeSearchBar() {
        viewModel.isSearchBarOpen.observe(viewLifecycleOwner) { isSearchBarOpen ->
            setUpSearchBar()
            if (isSearchBarOpen) {
                adapter.submitList(viewModel.searchedCharacters.value)
            } else {
                adapter.submitList(viewModel.charactersList.value)
            }
        }
    }
}