package com.example.syftreposearchapp.di

import com.example.syftreposearchapp.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class,
    RepositoryModule::class))
interface NetworkComponent {

    fun inject(mainActivity: MainActivity)
}