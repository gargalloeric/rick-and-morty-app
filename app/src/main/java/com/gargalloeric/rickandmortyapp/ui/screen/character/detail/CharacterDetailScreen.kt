package com.gargalloeric.rickandmortyapp.ui.screen.character.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gargalloeric.rickandmortyapp.R
import com.gargalloeric.rickandmortyapp.data.model.Character
import com.gargalloeric.rickandmortyapp.ui.theme.RickAndMortyAppTheme

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    status: CharacterDetailStatus,
    onBackClick: () -> Unit
) {
    when(status) {
        is CharacterDetailStatus.Error -> ErrorStatus(modifier = modifier, errorIcon = R.drawable.error_24px)
        is CharacterDetailStatus.Loading -> LoadingStatus(modifier = modifier)
        is CharacterDetailStatus.Success -> SuccessStatus(modifier = modifier, character = status.data, onBackClick = onBackClick)
    }
}

@Composable
fun ErrorStatus(
    modifier: Modifier = Modifier,
    @DrawableRes errorIcon: Int
) {
    val icon = painterResource(id = errorIcon)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(painter = icon, contentDescription = null)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Upsss... We have problems fetching the data.")
    }
}

@Composable
fun LoadingStatus(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Loading...")
    }
}

@Composable
fun SuccessStatus(
    modifier: Modifier = Modifier,
    character: Character,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            FilledTonalIconButton(onClick = onBackClick) {
                val icon = painterResource(id = R.drawable.arrow_back_24px)
                Icon(
                    painter = icon,
                    contentDescription = "Go back"
                )
            }
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = character.name, fontWeight = FontWeight.Bold, fontSize = 35.sp, lineHeight = 35.sp)
            Text(text = "${character.species} · ${character.status}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailScreenPreview() {
    RickAndMortyAppTheme {
        LoadingStatus(
            modifier = Modifier.fillMaxSize()
        )
    }
}