package com.example.syftreposearchapp.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.syftreposearchapp.data.repository.RepositoryImpl
import com.example.syftreposearchapp.ui.activity.MainActivity
import com.example.syftreposearchapp.viewmodel.MainViewModel
import com.example.syftreposearchapp.viewmodel.factory.MainViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule() {
    @Provides
    @Singleton
    fun provideMainViewModelFactory(repository: RepositoryImpl): MainViewModelFactory{
        return MainViewModelFactory(repository)
    }
}