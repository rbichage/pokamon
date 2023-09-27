package com.pokamon.features.pokedex.ui.listing

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pokamon.core.design.theme.PokamonTheme
import com.pokamon.features.networking.util.createImageUrl
import com.pokamon.features.pokedex.R
import com.pokamon.features.pokedex.domain.model.PokemonListing
import com.pokamon.features.pokedex.ui.util.UrlImageView
import timber.log.Timber
import java.util.UUID

val sampleItems = List(
    20,
) {
    PokemonListing(
        id = UUID.randomUUID().toString(),
        name = " Pokemon $it",
        imageUrl = createImageUrl((it + 1).toString())
    )
}

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    onItemClicked: (String) -> Unit
) {
    val viewModel = hiltViewModel<CharactersViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var items by remember {
        mutableStateOf(listOf<PokemonListing>())
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when (state) {
            is CharactersUIState.Error -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.an_error_occurred))
                }
            }

            CharactersUIState.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = modifier.size(48.dp)
                    )
                }
            }

            is CharactersUIState.Success -> {
                items = (state as CharactersUIState.Success).characters
                SearchContent()

                Spacer(modifier = modifier.height(16.dp))

                CharactersContent(
                    characters = items,
                    scrollState = scrollState,
                    onItemClicked = onItemClicked
                )
            }
        }

    }
}

@Composable
fun SearchContent(
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                Timber.e("search clicked")
            },
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(text = stringResource(R.string.tap_to_search))
        },
        shape = RoundedCornerShape(percent = 50),
        readOnly = true,
        enabled = false
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CharactersContent(
    modifier: Modifier = Modifier,
    characters: List<PokemonListing>,
    searchText: String = "",
    scrollState: ScrollState,
    onItemClicked: (String) -> Unit
) {
    val filtered = if (searchText.isNotBlank()) {
        characters.filter {
            it.name.contains(searchText, true)
        }
    } else {
        characters
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Pokemons",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = modifier.height(24.dp))
        if (filtered.isEmpty()) {
            Text(text = "Nothing found")
        } else {
            FlowRow(
                modifier = modifier.fillMaxWidth(),
                maxItemsInEachRow = 2,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                filtered.forEach { character ->
                    Card(
                        modifier = modifier
                            .fillMaxWidth(.48F),
                        onClick = {
                            onItemClicked(character.id)
                        },
                    ) {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            UrlImageView(
                                url = character.imageUrl,
                                imageSize = 150.dp
                            )

                            Text(
                                modifier = modifier
                                    .align(Alignment.BottomCenter),
                                text = character.name
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterPreview() {
    val modifier = Modifier
    PokamonTheme {
        Surface(
            modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SearchContent()

                Spacer(modifier = modifier.height(16.dp))
                CharactersContent(
                    characters = sampleItems,
                    scrollState = rememberScrollState(),
                    onItemClicked = {}
                )
            }
        }
    }
}