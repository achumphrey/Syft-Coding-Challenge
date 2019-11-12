package com.example.syftreposearchapp.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syftreposearchapp.data.model.Item
import com.example.syftreposearchapp.data.repository.Repository
import com.example.syftreposearchapp.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class MainViewModel constructor(private val repository: Repository) : ViewModel() {
    private val disposable = CompositeDisposable()


    fun fetchRepos(query: String, language: String, currentPageNo: Int = 1, clearOldItem: Boolean) {
        if (clearOldItem) {
            loadingState.value = LoadingState.LOADING
        }
        disposable.add(
            repository.fetchGitRepos(
                query + if (language != "") {
                    " language:$language"
                } else {
                    ""
                }, currentPageNo
            )
                .subscribe({
                    lastFetchedTime = Date()
                    if (it.items.isEmpty()) {
                        if (clearOldItem) {
                            errorMessage.value = "No Repos Found"
                            loadingState.value = LoadingState.ERROR
                        } else {
                            toastMessage.value = "No more data found"
                        }
                    } else {
                        repos.value = it.items
                        totalCount.value = it.totalCount
                        loadingState.value = LoadingState.SUCCESS
                    }
                }, {
                    lastFetchedTime = Date()

                    it.printStackTrace()

                    if (clearOldItem) {
                        when (it) {
                            is UnknownHostException -> errorMessage.value = "No Network"
                            else -> errorMessage.value = it.localizedMessage
                        }

                        loadingState.value = LoadingState.ERROR
                    } else {
                        when (it) {
                            is UnknownHostException -> toastMessage.value = "No Network"
                            else -> toastMessage.value = it.localizedMessage
                        }
                    }
                })
        )
    }

    var lastFetchedTime: Date? = null

    val repos: MutableLiveData<List<Item>> = MutableLiveData()
    val totalCount: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()

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