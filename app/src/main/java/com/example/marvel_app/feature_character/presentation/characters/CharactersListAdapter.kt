package com.example.marvel_app.feature_character.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.databinding.ListItemDiscoverBinding
import com.example.marvel_app.feature_character.domain.models.Character

class CharactersListAdapter() :
    ListAdapter<Character, CharactersListAdapter.CharacterViewHolder>(DiffCallback) {

    class CharacterViewHolder(var binding: ListItemDiscoverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.character = character
            binding.executePendingBindings()

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(
            ListItemDiscoverBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }
}