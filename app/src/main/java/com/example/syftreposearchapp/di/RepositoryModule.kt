package com.example.syftreposearchapp.di

import com.example.syftreposearchapp.data.repository.RepositoryImpl
import com.example.syftreposearchapp.viewmodel.factory.MainViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMainViewModelFactory(repository: RepositoryImpl): MainViewModelFactory{
        return MainViewModelFactory(repository)
    }
}