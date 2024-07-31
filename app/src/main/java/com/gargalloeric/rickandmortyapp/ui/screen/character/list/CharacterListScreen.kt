package com.gargalloeric.rickandmortyapp.ui.screen.character.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gargalloeric.rickandmortyapp.data.model.Character
import kotlinx.coroutines.flow.Flow

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    pagingDataFlow: Flow<PagingData<Character>>,
    onClick: (Long) -> Unit
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
                CharacterItem(
                    modifier = Modifier.padding(vertical = 8.dp),
                    character = character,
                    onClick = onClick
                )
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

@Composable
fun CharacterItem(
    modifier: Modifier,
    character: Character,
    onClick: (Long) -> Unit
) {
    ListItem(
        modifier = modifier
            .clickable { onClick(character.id) },
        headlineContent = {
            Row {
                AsyncImage(
                    modifier = Modifier.clip(RoundedCornerShape(5.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = character.name, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Text(text = "${character.species} · ${character.status}")
                }
            }
        }
    )
}