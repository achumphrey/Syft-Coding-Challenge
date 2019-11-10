package com.example.syftreposearchapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.syftreposearchapp.R
import com.example.syftreposearchapp.data.model.Items
import com.example.syftreposearchapp.ui.viewholder.RepoViewHolder
import com.example.syftreposearchapp.utils.inflate


class RepoAdapter constructor(private val repos: MutableList<Items>) :
    RecyclerView.Adapter<RepoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {

        val view: View = parent.inflate(R.layout.view_holder_repo, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindItem(repos[holder.adapterPosition])
    }

    fun setItems(items: List<Items>) {
        repos.clear()
        repos.addAll(items)
        notifyDataSetChanged()
    }
}
