package com.gargalloeric.rickandmortyapp.ui.screen.character.detail

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.gargalloeric.rickandmortyapp.RickAndMortyApplication
import com.gargalloeric.rickandmortyapp.data.character.CharacterRepository
import com.gargalloeric.rickandmortyapp.data.model.Character
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CharacterDetailStatus {
    data object Loading : CharacterDetailStatus
    data object Error : CharacterDetailStatus
    data class  Success(val data: Character) : CharacterDetailStatus
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository
): ViewModel() {
    private var _uiState = MutableStateFlow<CharacterDetailStatus>(CharacterDetailStatus.Loading)
    val uiStatus = _uiState.asStateFlow()

    private val characterId: String = checkNotNull(savedStateHandle["characterId"])

    init {
        getCharacter()
    }

    fun getCharacter() {
        viewModelScope.launch {
            _uiState.value = try {
                CharacterDetailStatus.Success(
                    repository.getCharacter(characterId)
                )
            } catch (e: IOException) {
                CharacterDetailStatus.Error
            } catch (e: HttpException) {
                CharacterDetailStatus.Error
            }
        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val repository = (application as RickAndMortyApplication).container.characterRepository

                val savedStateHandle = extras.createSavedStateHandle()

                return CharacterDetailViewModel(
                    savedStateHandle,
                    repository
                ) as T
            }
        }
    }
}