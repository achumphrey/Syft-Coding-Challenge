package com.example.syftreposearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.syftreposearchapp.R
import com.example.syftreposearchapp.ui.viewholder.LanguageViewHolder


class LanguageAdapter(private val languages: List<String>, var selectedLanguage: String) :
    RecyclerView.Adapter<LanguageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_language, parent,
                false )
        )
    }

    override fun getItemCount() = languages.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(languages[holder.adapterPosition], selectedLanguage) { language ->
            selectedLanguage = language
            notifyDataSetChanged()
        }
    }

}