package com.example.marvel_app.feature_character.presentation_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R

class CharactersFragment : Fragment(R.layout.fragment_discover) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.discover_grid_recycler_view)
        val adapter = recyclerView?.adapter as CharactersListAdapter
        return null

    }

}