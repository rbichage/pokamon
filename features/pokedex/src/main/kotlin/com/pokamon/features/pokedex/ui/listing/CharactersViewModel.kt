package com.pokamon.features.pokedex.ui.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokamon.features.networking.di.IODispatcher
import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.domain.mapper.GetPokemonsResult
import com.pokamon.features.pokedex.domain.model.PokemonListing
import com.pokamon.features.pokedex.domain.usecase.GetPokemons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getPokemons: GetPokemons,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val getCharactersEvent = Channel<Unit>(Channel.CONFLATED)

    init {
        getCharacters()
    }

    val uiState = getCharactersEvent
        .receiveAsFlow()
        .flatMapMerge {
            getPokemons.execute()
        }.map { result ->
            mapToUIState(result)
        }.flowOn(coroutineDispatcher)
        .stateIn(
            scope = viewModelScope,
            initialValue = CharactersUIState.Loading,
            started = SharingStarted.WhileSubscribed(5000L)
        )

    fun getCharacters() {
        getCharactersEvent.trySend(Unit)
    }

    fun filterCharacters(
        searchText: String = "",
        characters: List<PokemonListing> = emptyList()
    ) {
//        getCharactersEvent.trySend(Request(searchText, characters))
    }

    private fun mapToUIState(
        result: GetPokemonsResult
    ): CharactersUIState {
        return when (result) {
            is BaseResult.Failure -> {
                when (val error = result.error) {
                    is GetPokemons.Errors.HttpError -> {
                        CharactersUIState.Error(
                            errorType = ErrorType.HttpError(
                                error.message
                            )
                        )
                    }

                    GetPokemons.Errors.NetworkError -> {
                        CharactersUIState.Error(
                            errorType = ErrorType.NetworkError
                        )
                    }

                    GetPokemons.Errors.UnknownError -> {
                        CharactersUIState.Error(
                            errorType = ErrorType.UnknownError
                        )
                    }
                }

            }

            is BaseResult.Loading -> {
                CharactersUIState.Loading
            }

            is BaseResult.Success -> {
                CharactersUIState.Success(
                    characters = result.data,
                )
            }
        }
    }

}

sealed interface CharactersUIState {
    data object Loading : CharactersUIState
    data class Success(val characters: List<PokemonListing>) : CharactersUIState
    data class Error(val errorType: ErrorType) : CharactersUIState
}

sealed interface ErrorType {
    data object NetworkError : ErrorType
    data object UnknownError : ErrorType
    data class HttpError(val message: String) : ErrorType
}