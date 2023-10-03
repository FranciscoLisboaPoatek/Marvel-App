package com.example.marvel_app.feature_character.presentation.components.marvel_top_app_bar

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ComponentMarvelTopAppBarBinding
import com.google.android.material.appbar.MaterialToolbar

class MarvelTopAppBarInflater(
    marvelTopAppBar: ComponentMarvelTopAppBarBinding
) {
    private var marvelLogo: ImageView = marvelTopAppBar.marvelTopAppBarLogo
    private var topBar: MaterialToolbar = marvelTopAppBar.marvelTopAppBarToolbar
    private var searchBar: EditText = marvelTopAppBar.marvelTopAppBarSearchText

    fun setupMarvelAppTopBar(fragment: Fragment, viewModel: MarvelTopAppBarViewModel) {
        topBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_item -> {
                    viewModel.switchIsSearchBarOpen()
                    true
                }

                else -> false
            }
        }

        viewModel.isSearchBarOpen.observe(fragment.viewLifecycleOwner) { isOpen ->
            setUpSearchBar(isOpen, fragment.requireContext())
        }
    }

    private fun setUpSearchBar(isOpen: Boolean, context: Context) {

        val imm =
            context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val menuItem = topBar.menu.findItem(R.id.search_item)

        if (isOpen) {
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
}