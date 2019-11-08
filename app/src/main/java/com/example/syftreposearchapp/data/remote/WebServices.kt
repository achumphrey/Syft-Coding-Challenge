package com.example.syftreposearchapp.data.remote

import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.data.model.GitRepos
import com.example.syftreposearchapp.utils.Constant
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface WebServices {
    companion object {
        val instance: WebServices by lazy {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(WebServices::class.java)
        }
    }

    @GET(Constant.endpointUrl)
    fun fetchRepoWebService(@Query("q") org : String?,
                             @Query("sort") sort : String?,
                             @Query("order") order : String?): Single<GitRepoModel>
}