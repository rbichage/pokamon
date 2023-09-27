package com.pokamon.features.pokedex.ui.listing

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pokamon.features.pokedex.R
import com.pokamon.features.pokedex.domain.model.PokemonListing
import com.pokamon.features.pokedex.ui.util.UrlImageView


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchBottomSheet(
    modifier: Modifier = Modifier,
    pokemon: List<PokemonListing>,
    onDismiss: (String) -> Unit
) {
    val config = LocalConfiguration.current
    val height = (config.screenHeightDp * .90).toInt()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val scrollState = rememberScrollState()

    var searchText by remember {
        mutableStateOf("")
    }

    ModalBottomSheet(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp),
        onDismissRequest = {
            onDismiss("")
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(height.dp)
                .padding(all = 16.dp)
        ) {
            TextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.start_typing),
                    )
                },
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            Spacer(modifier = modifier.height(16.dp))

            if (searchText.isNotEmpty()) {

                val filtered = pokemon.filter { it.name.contains(searchText, true) }
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
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
                                    onDismiss(character.id)
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
    }
}
