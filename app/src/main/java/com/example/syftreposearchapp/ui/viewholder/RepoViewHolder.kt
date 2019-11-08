package com.example.syftreposearchapp.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.data.model.GitRepos
import com.example.syftreposearchapp.data.model.Items
import kotlinx.android.synthetic.main.cardview_rv.view.*

class RepoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(repos: MutableList<Items>) {
        itemView.tvDesc.text = repos[adapterPosition].description
    }
}