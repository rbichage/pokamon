package com.pokamon.features.pokedex.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokamon.features.networking.di.IODispatcher
import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.domain.model.Pokemon
import com.pokamon.features.pokedex.domain.usecase.GetPokemonDetails
import com.pokamon.features.pokedex.ui.listing.ErrorType
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
class CharacterDetailsViewModel @Inject constructor(
    private val getPokemonDetails: GetPokemonDetails,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val getDetailsEvent = Channel<String>(Channel.CONFLATED)

    val uIState = getDetailsEvent
        .receiveAsFlow()
        .flatMapMerge {
            getPokemonDetails.execute(it)
        }.map { result ->
            mapToUIState(result)
        }.flowOn(coroutineDispatcher)
        .stateIn(
            scope = viewModelScope,
            initialValue = CharacterDetailsUIState.Idle,
            started = SharingStarted.WhileSubscribed(5000L)
        )

    fun getDetails(id: String) {
        getDetailsEvent.trySend(id)
    }

    private fun mapToUIState(
        result: BaseResult<Pokemon, GetPokemonDetails.Errors>
    ): CharacterDetailsUIState {
        return when (result) {
            is BaseResult.Loading -> CharacterDetailsUIState.Loading
            is BaseResult.Failure -> {
                when (val error = result.error) {
                    is GetPokemonDetails.Errors.HttpError -> {
                        CharacterDetailsUIState.Error(
                            errorType = ErrorType.HttpError(
                                message = error.message
                            )
                        )
                    }

                    GetPokemonDetails.Errors.NetworkError -> {
                        CharacterDetailsUIState.Error(
                            errorType = ErrorType.NetworkError
                        )
                    }

                    GetPokemonDetails.Errors.UnknownError -> {
                        CharacterDetailsUIState.Error(
                            errorType = ErrorType.UnknownError
                        )
                    }
                }
            }

            is BaseResult.Success -> CharacterDetailsUIState.Success(result.data)
        }
    }
}

sealed interface CharacterDetailsUIState {
    data object Idle : CharacterDetailsUIState
    data object Loading : CharacterDetailsUIState
    data class Success(val pokemon: Pokemon) : CharacterDetailsUIState
    data class Error(val errorType: ErrorType) : CharacterDetailsUIState
}