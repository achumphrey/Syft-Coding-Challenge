package com.example.syftreposearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.syftreposearchapp.R
import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.data.model.GitRepos
import com.example.syftreposearchapp.data.model.Items
import com.example.syftreposearchapp.ui.viewholder.RepoViewHolder
import com.example.syftreposearchapp.utils.inflate



class RepoAdapter constructor(val repos: MutableList<Items>) :
    RecyclerView.Adapter<RepoViewHolder>(), Filterable {

    lateinit var filteredListItems: MutableList<Items>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder{

        val view: View = parent.inflate(R.layout.cardview_rv, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bindItem(repos)
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredListItems = repos
                } else {
                     var filteredList = mutableListOf<Items>()
                    for (row in repos) {

                        if (row.description.toLowerCase().contains(charString.toLowerCase()))

                            filteredList.add(row)
                    }
                            filteredListItems = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredListItems
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                filteredListItems = filterResults.values as MutableList<Items>
                notifyDataSetChanged()
            }
        }
    }
}
