package com.example.marvel_app.feature_character.presentation.favorite_characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.databinding.ListItemFavoriteBinding
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.presentation.ImageType
import com.example.marvel_app.feature_character.presentation.bindImage
import com.example.marvel_app.feature_character.presentation.makeImageUrl

class FavoriteCharactersListAdapter(private val clickListener: FavoriteCharacterClickListener) :
    ListAdapter<Character, FavoriteCharactersListAdapter.FavoriteCharacterViewHolder>(DiffCallback) {

    class FavoriteCharacterViewHolder(private var binding: ListItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character, clickListener: FavoriteCharacterClickListener) {
            binding.character = character
            binding.clickListener = clickListener
            bindImage(binding.listItemFavoriteCharacterImageView,
                makeImageUrl(character.imgPath,character.imgExtension, ImageType.FAVORITE))
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoriteCharacterViewHolder(
            ListItemFavoriteBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteCharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character, clickListener)
    }
}

class FavoriteCharacterClickListener(val clickListener: (character: Character) -> Unit) {
    fun onClick(character: Character) = clickListener(character)
}