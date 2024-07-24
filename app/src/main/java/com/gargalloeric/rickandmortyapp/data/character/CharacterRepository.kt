package com.gargalloeric.rickandmortyapp.data.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gargalloeric.rickandmortyapp.data.model.Character
import com.gargalloeric.rickandmortyapp.network.CharacterApiService
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
}

class NetworkCharacterRepository(
    private val apiService: CharacterApiService
): CharacterRepository {
    override fun getCharacters(): Flow<PagingData<Character>> = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 15
        ),
        pagingSourceFactory = { CharacterPagingSource(apiService) }
    ).flow
}