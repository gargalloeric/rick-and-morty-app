package com.gargalloeric.rickandmortyapp

import android.app.Application
import com.gargalloeric.rickandmortyapp.data.AppContainer
import com.gargalloeric.rickandmortyapp.data.DefaultContainer

class RickAndMortyApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}