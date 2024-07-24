package com.gargalloeric.rickandmortyapp.ui.screen.character.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.gargalloeric.rickandmortyapp.data.model.Character
import kotlinx.coroutines.flow.Flow

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    pagingDataFlow: Flow<PagingData<Character>>
) {
    val characters = pagingDataFlow.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier) {
        if (characters.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "Waiting for items to load from the backend",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        items(count = characters.itemCount) { index ->
            characters[index]?.let { character ->
                Text(text = character.name)
            }
        }

        if (characters.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}