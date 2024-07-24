package com.gargalloeric.rickandmortyapp.network

import com.gargalloeric.rickandmortyapp.data.model.CharacterResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CharacterApiService(
    private val httpClient: HttpClient
) {
    private val BASE_URL = "https://rickandmortyapi.com/api/character"
    suspend fun getCharacters(page: Int): CharacterResult = httpClient.get(BASE_URL) {
        url {
            parameters.append("page", page.toString())
        }
    }.body()
}