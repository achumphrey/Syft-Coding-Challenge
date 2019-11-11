package com.example.syftreposearchapp.data.repository

import com.example.syftreposearchapp.data.model.GitRepoModel
import io.reactivex.Single

interface Repository {
    fun fetchGitRepos(query: String): Single<GitRepoModel>
}