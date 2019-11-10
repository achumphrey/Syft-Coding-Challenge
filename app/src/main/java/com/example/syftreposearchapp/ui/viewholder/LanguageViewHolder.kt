package com.example.syftreposearchapp.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_holder_language.view.*

class LanguageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        language: String,
        selectedLanguage: String,
        languageClicked: (language: String) -> Unit
    ) {
        itemView.tvLanguage.text = language
        itemView.rbLanguage.isChecked = language == selectedLanguage
        itemView.setOnClickListener {
            languageClicked(language)
        }
        itemView.rbLanguage.setOnClickListener {
            languageClicked(language)
        }
    }
}
