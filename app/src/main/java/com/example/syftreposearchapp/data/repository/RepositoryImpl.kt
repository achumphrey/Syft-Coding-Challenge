package com.example.syftreposearchapp.data.repository

import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.data.model.GitRepos
import com.example.syftreposearchapp.data.remote.WebServices
import com.example.syftreposearchapp.utils.Constant
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RepositoryImpl(private val webServices: WebServices) : Repository {
    override fun fetchGitRepos(query: String): Single<GitRepoModel> {
        return webServices.fetchRepoWebService(query, Constant.sort, Constant.order)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}