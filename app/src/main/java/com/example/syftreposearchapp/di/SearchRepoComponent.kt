package com.example.syftreposearchapp.di

import com.example.syftreposearchapp.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface SearchRepoComponent {

    fun inject(mainActivity: MainActivity)
}