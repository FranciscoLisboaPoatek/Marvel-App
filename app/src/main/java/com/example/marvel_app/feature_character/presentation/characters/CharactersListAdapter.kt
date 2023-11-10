package com.example.marvel_app.feature_character.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.databinding.ListItemDiscoverBinding
import com.example.marvel_app.feature_character.domain.models.Character
import com.example.marvel_app.feature_character.presentation.ImageType
import com.example.marvel_app.feature_character.presentation.bindImage
import com.example.marvel_app.feature_character.presentation.makeImageUrl

class CharactersListAdapter(private val clickListener: CharacterClickListener,
                            private val favoriteClickListener: CharacterClickListener,
                            private val isItemFavorited: (Character) -> Unit) :
    ListAdapter<Character, CharactersListAdapter.CharacterViewHolder>(DiffCallback) {

    class CharacterViewHolder(private var binding: ListItemDiscoverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character, clickListener: CharacterClickListener,favoriteClickListener: CharacterClickListener, isItemFavorited: (Character) -> Unit) {
            binding.character = character
            binding.clickListener = clickListener
            bindImage(binding.listItemDiscoverCharacterImageView,
                makeImageUrl(character.imgPath,character.imgExtension,ImageType.DISCOVER)
            )

            isItemFavorited(character)
            setFavoriteIcon(character)

            binding.listItemDiscoverFavoriteIcon.setOnClickListener{
                favoriteClickListener.onClick(character)
                setFavoriteIcon(character)
            }
            binding.executePendingBindings()

        }
        private fun setFavoriteIcon(character: Character){
            if(character.isFavorited){
                binding.listItemDiscoverFavoriteIcon.setImageResource(R.drawable.ic_favorite_checked)
            } else {
                binding.listItemDiscoverFavoriteIcon.setImageResource(R.drawable.ic_favorite_unchecked)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name && oldItem.isFavorited == newItem.isFavorited
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
        holder.bind(character,clickListener,favoriteClickListener,isItemFavorited)
    }
}

class CharacterClickListener(val clickListener: (character: Character) -> Unit){
    fun onClick(character: Character) = clickListener(character)
}