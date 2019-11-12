package com.example.syftreposearchapp.data.repository

import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.data.remote.WebServices
import com.example.syftreposearchapp.utils.Constant
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class RepositoryImpl @Inject constructor(private val webServices: WebServices) : Repository {
    override fun fetchGitRepos(query: String, currentPageNo: Int): Single<GitRepoModel> {
        return webServices.fetchRepoWebService(query, Constant.sort, Constant.order,currentPageNo )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}