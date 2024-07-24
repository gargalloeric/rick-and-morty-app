package com.gargalloeric.rickandmortyapp.ui.screen.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gargalloeric.rickandmortyapp.RickAndMortyApplication
import com.gargalloeric.rickandmortyapp.data.character.CharacterRepository
import com.gargalloeric.rickandmortyapp.data.model.Character
import kotlinx.coroutines.flow.Flow

class CharacterListViewModel(
    private val repository: CharacterRepository
): ViewModel() {
    fun getCharacters(): Flow<PagingData<Character>> = repository.getCharacters().cachedIn(viewModelScope)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RickAndMortyApplication)
                val repository = application.container.characterRepository
                CharacterListViewModel(repository)
            }
        }
    }
}