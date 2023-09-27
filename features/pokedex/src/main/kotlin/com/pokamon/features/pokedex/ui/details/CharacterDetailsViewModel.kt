package com.pokamon.features.pokedex.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokamon.features.networking.di.IODispatcher
import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.domain.model.Character
import com.pokamon.features.pokedex.domain.usecase.GetCharacterDetails
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
    private val getCharacterDetails: GetCharacterDetails,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val getDetailsEvent = Channel<String>(Channel.CONFLATED)

    val uIState = getDetailsEvent
        .receiveAsFlow()
        .flatMapMerge {
            getCharacterDetails.execute(it)
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
        result: BaseResult<Character, GetCharacterDetails.Errors>
    ): CharacterDetailsUIState {
        return when (result) {
            is BaseResult.Loading -> CharacterDetailsUIState.Loading
            is BaseResult.Failure -> {
                when (val error = result.error) {
                    is GetCharacterDetails.Errors.HttpError -> {
                        CharacterDetailsUIState.Error(
                            errorType = ErrorType.HttpError(
                                message = error.message
                            )
                        )
                    }

                    GetCharacterDetails.Errors.NetworkError -> {
                        CharacterDetailsUIState.Error(
                            errorType = ErrorType.NetworkError
                        )
                    }

                    GetCharacterDetails.Errors.UnknownError -> {
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
    data class Success(val character: Character) : CharacterDetailsUIState
    data class Error(val errorType: ErrorType) : CharacterDetailsUIState
}