package com.example.syftreposearchapp.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syftreposearchapp.data.model.Items
import com.example.syftreposearchapp.data.repository.Repository
import com.example.syftreposearchapp.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class MainViewModel constructor(private val repository: Repository) : ViewModel() {
    private val disposable = CompositeDisposable()

    fun fetchRepos(query: String = "org:github", language: String = "") {
        loadingState.value = LoadingState.LOADING
        disposable.add(
            repository.fetchGitRepos(
                query + if (language != "") {
                    " language:$language"
                } else {
                    ""
                }
            )
                .subscribe({
                    lastFetchedTime = Date()
                    if (it == null) {
                        errorMessage.value = "No Repos Found"
                        loadingState.value = LoadingState.ERROR
                    } else {
                        repos.value = it.items
                        totalCount.value = it.total_count
                        loadingState.value = LoadingState.SUCCESS
                    }
                }, {
                    lastFetchedTime = Date()

                    it.printStackTrace()
                    when (it) {
                        is UnknownHostException -> errorMessage.value = "No Network"
                        else -> errorMessage.value = it.localizedMessage
                    }

                    loadingState.value = LoadingState.ERROR
                })
        )
    }

    var lastFetchedTime: Date? = null

    val repos: MutableLiveData<List<Items>> = MutableLiveData()
    val totalCount: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val loadingState = MutableLiveData<LoadingState>()

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getActivity(): Class<out Activity> {
        return MainActivity::class.java
    }
}