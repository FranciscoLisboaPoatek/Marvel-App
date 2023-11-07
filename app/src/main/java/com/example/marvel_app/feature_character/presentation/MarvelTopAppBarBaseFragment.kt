package com.example.marvel_app.feature_character.presentation

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
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

abstract class MarvelTopAppBarBaseFragment<B : ViewBinding, VH : RecyclerView.ViewHolder> :
    BaseFragment<B>() {
    abstract val marvelTopAppBar: ComponentMarvelTopAppBarBinding
    abstract val viewModel: MarvelTopAppBarViewModel
    abstract val adapter: ListAdapter<Character, VH>

    private val searchRunnable = Runnable {
        viewModel.searchText.value?.let { viewModel.searchCharacters(0, it) }
    }
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var imm: InputMethodManager
    fun setupMarvelAppTopBar() {
        marvelTopAppBar.viewModel = viewModel

        imm = this.requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        marvelTopAppBar.marvelTopAppBarToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_item -> {
                    viewModel.switchIsSearchBarOpen()
                    if (viewModel.isSearchBarOpen.value == true) {
                        saveListPosition()
                        imm.showSoftInput(
                            marvelTopAppBar.marvelTopAppBarSearchText,
                            InputMethodManager.SHOW_IMPLICIT
                        )
                    } else adjustListPosition()

                    true
                }

                else -> false
            }
        }

        setUpSearchBar()
        observeSearchBar()
        observeSearchText()
        observeSearchedCharacterList()
    }

    private fun setUpSearchBar() {
        val isOpen = viewModel.isSearchBarOpen.value
        val searchBar: EditText = marvelTopAppBar.marvelTopAppBarSearchText
        val marvelLogo: ImageView = marvelTopAppBar.marvelTopAppBarLogo
        val context = this.requireContext()

        val menuItem = marvelTopAppBar.marvelTopAppBarToolbar.menu.findItem(R.id.search_item)

        if (isOpen == true) {
            menuItem.icon =
                ContextCompat.getDrawable(context, R.drawable.close_24px)
            viewModel.foundSearchResults.value?.let { showNoResultsFound(!it) }
            marvelLogo.visibility = View.GONE
            searchBar.visibility = View.VISIBLE
            searchBar.requestFocus()
            setScreenStatus(true)
        } else {
            menuItem.icon =
                ContextCompat.getDrawable(context, R.drawable.ic_search)
            showNoResultsFound(false)
            marvelLogo.visibility = View.VISIBLE
            searchBar.visibility = View.GONE
            imm.hideSoftInputFromWindow(searchBar.windowToken, 0)
            setScreenStatus(false)
        }
    }
    abstract fun setScreenStatus(isSearchBarOpen:Boolean)
    protected fun enableSearch(enable: Boolean) {
        val searchMenuItem = marvelTopAppBar.marvelTopAppBarToolbar.menu.findItem(R.id.search_item)

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


    abstract fun saveListPosition()
    abstract fun adjustListPosition()
    private fun observeSearchedCharacterList() {
        viewModel.searchedCharacters.observe(viewLifecycleOwner) { searchedCharactersList ->
            if (viewModel.isSearchBarOpen.value == true) {
                adapter.submitList(searchedCharactersList, Runnable {
                    adjustListPosition()
                })
            }
        }
        viewModel.foundSearchResults.observe(viewLifecycleOwner) { foundSearchResults ->
            if (viewModel.isSearchBarOpen.value == true) {
                showNoResultsFound(!foundSearchResults)
            }
        }
    }

    abstract fun showNoResultsFound(notFound: Boolean)
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

    private fun observeSearchText() {

        viewModel.searchText.observe(viewLifecycleOwner) { searchText ->
            handler.removeCallbacks(searchRunnable)
            if (searchText.isBlank() || searchText == viewModel.oldSearchText) {
                viewModel.setOldText(searchText)
                return@observe
            }
            viewModel.setOldText(searchText)
            handler.postDelayed(searchRunnable, 1000)
        }
    }

    override fun onDestroyView() {
        handler.removeCallbacks(searchRunnable)
        super.onDestroyView()
    }

}