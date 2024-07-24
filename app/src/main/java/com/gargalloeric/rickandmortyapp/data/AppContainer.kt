package com.gargalloeric.rickandmortyapp.data

import com.gargalloeric.rickandmortyapp.data.character.CharacterRepository
import com.gargalloeric.rickandmortyapp.data.character.NetworkCharacterRepository
import com.gargalloeric.rickandmortyapp.network.CharacterApiService
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

interface AppContainer {
    val characterRepository: CharacterRepository
}

class DefaultContainer: AppContainer {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
    }

    private val characterApiService: CharacterApiService = CharacterApiService(httpClient)

    override val characterRepository: CharacterRepository by lazy {
        NetworkCharacterRepository(characterApiService)
    }
}