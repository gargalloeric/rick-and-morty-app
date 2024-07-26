package com.gargalloeric.rickandmortyapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gargalloeric.rickandmortyapp.ui.screen.character.list.CharacterListScreen
import com.gargalloeric.rickandmortyapp.ui.screen.character.list.CharacterListViewModel

enum class Screen {
    CharacterList
}

@Composable
fun RickAndMoryApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.CharacterList.name
        ) {
            composable(route = Screen.CharacterList.name) {
                val characterListViewModel: CharacterListViewModel = viewModel(factory = CharacterListViewModel.Factory)
                CharacterListScreen(
                    modifier = Modifier.fillMaxSize(),
                    pagingDataFlow = characterListViewModel.getCharacters(),
                    onClick = { /*TODO: Implement navigation to detail*/ }
                )
            }
        }
    }
}