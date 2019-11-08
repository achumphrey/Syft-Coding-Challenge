package com.example.syftreposearchapp.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.data.model.GitRepos
import com.example.syftreposearchapp.data.model.Items
import com.example.syftreposearchapp.data.repository.Repository
import com.example.syftreposearchapp.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class MainViewModel constructor(private val repository: Repository) : ViewModel() {
    private val disposable = CompositeDisposable()

    fun fetchrepos() {
        loadingState.value = LoadingState.LOADING
        disposable.add(
            repository.fetchGitRepos()

                .subscribe({
                    lastFetchedTime = Date()
                    if (it == null) {
                        errorMessage.value = "No Post Found"
                        loadingState.value = LoadingState.ERROR
                    } else {
                        it.let { val list:MutableList<Items> = it.items as MutableList<Items>
                            repos.value = list}

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

    val repos: MutableLiveData<MutableList<Items>> = MutableLiveData()

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

    fun getActivity(): Class<out Activity>{
        return MainActivity::class.java
    }
}