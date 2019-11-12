package com.example.syftreposearchapp.data.remote

import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.utils.Constant
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServices {

    @GET(Constant.endpointUrl)
    fun fetchRepoWebService(
        @Query("q") org: String?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") currentPageNo: Int
    ): Single<GitRepoModel>
}