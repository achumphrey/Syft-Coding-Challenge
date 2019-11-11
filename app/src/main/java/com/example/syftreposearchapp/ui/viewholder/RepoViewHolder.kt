package com.example.syftreposearchapp.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.syftreposearchapp.data.model.Item
import kotlinx.android.synthetic.main.view_holder_repo.view.*

class RepoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(repo: Item) {
        itemView.tvDesc.text = repo.description
    }
}