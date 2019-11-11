package com.example.syftreposearchapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.syftreposearchapp.data.repository.Repository
import com.example.syftreposearchapp.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory constructor(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}