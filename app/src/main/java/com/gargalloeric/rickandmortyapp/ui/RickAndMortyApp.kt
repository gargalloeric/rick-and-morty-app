package com.gargalloeric.rickandmortyapp.ui

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gargalloeric.rickandmortyapp.R
import com.gargalloeric.rickandmortyapp.ui.screen.character.detail.CharacterDetailScreen
import com.gargalloeric.rickandmortyapp.ui.screen.character.detail.CharacterDetailViewModel
import com.gargalloeric.rickandmortyapp.ui.screen.character.list.CharacterListScreen
import com.gargalloeric.rickandmortyapp.ui.screen.character.list.CharacterListViewModel

enum class Screen {
    CharacterList,
    CharacterDetail
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RickAndMoryApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    val appIcon = painterResource(id = R.drawable.icons8_rick_sanchez)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = appIcon,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Rick And Morty",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
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
                    onClick = { characterId -> navController.navigate("${Screen.CharacterDetail.name}/$characterId") }
                )
            }

            composable(route = "${Screen.CharacterDetail.name}/{characterId}") {
                val characterDetailViewModel: CharacterDetailViewModel = viewModel(factory = CharacterDetailViewModel.Factory)
                val characterDetailScreenState by characterDetailViewModel.uiStatus.collectAsState()
                CharacterDetailScreen(
                    modifier = Modifier.fillMaxSize(),
                    status = characterDetailScreenState,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}