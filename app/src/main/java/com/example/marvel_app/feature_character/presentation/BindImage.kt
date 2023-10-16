package com.example.marvel_app.feature_character.presentation

import android.widget.ImageView
import androidx.core.net.toUri
import coil.load
import com.example.marvel_app.R

enum class ImageType { DISCOVER, FAVORITE, DETAIL }

fun makeImageUrl(imgPath: String, imgExtension: String, imgType: ImageType): String {
    return when (imgType) {
        ImageType.DISCOVER -> {
            "${imgPath}/portrait_xlarge.${imgExtension}"
        }

        ImageType.FAVORITE -> {
            "${imgPath}/landscape_amazing.${imgExtension}"

        }

        ImageType.DETAIL -> {
            "${imgPath}/standard_incredible.${imgExtension}"

        }
    }
}

fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        imgView.load(imgUri)
    }
}

