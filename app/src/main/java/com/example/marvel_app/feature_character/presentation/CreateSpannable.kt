package com.example.marvel_app.feature_character.presentation

import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan

object CreateSpannable {
    fun createSpannable(firstString: String, secondString: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder()

        // Cria e configura o primeiro Span
        val firstPartSpan = SpannableString(firstString)
        firstPartSpan.setSpan(AbsoluteSizeSpan(14, true), 0, firstString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Cria e configura o segundo Span
        val secondPartSpan = SpannableString(secondString)
        secondPartSpan.setSpan(AbsoluteSizeSpan(24, true), 0, secondString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Adiciona effect negrito (bold) para o segundo Span
        secondPartSpan.setSpan(StyleSpan(Typeface.BOLD), 0, secondString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Adiciona as partes ao Spannable
        spannable.append(firstPartSpan)
        spannable.append(secondPartSpan)

        return spannable
    }
}