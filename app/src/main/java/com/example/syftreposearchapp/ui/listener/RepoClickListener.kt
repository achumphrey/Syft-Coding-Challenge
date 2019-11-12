package com.mycakes.ui.adapter.listener

import com.example.syftreposearchapp.data.model.GitRepoModel


interface RepoClickListener {
    fun onClick(repos: GitRepoModel)
}
