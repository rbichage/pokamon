package com.pokamon.features.pokedex.domain.usecase

import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.data.repository.PokedexRepository
import com.pokamon.features.pokedex.domain.mapper.CharacterDetailsMapper
import com.pokamon.features.pokedex.domain.model.Character
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

typealias CharacterDetailsResult = BaseResult<
        Character, GetCharacterDetails.Errors>

class GetCharacterDetails @Inject constructor(
    private val repository: PokedexRepository,
    private val characterDetailsMapper: CharacterDetailsMapper
) {
    sealed interface Errors {
        data object NetworkError : Errors
        data object UnknownError : Errors
        data class HttpError(
            val message: String
        ) : Errors
    }

    fun execute(id: String) = flow {
        emit(BaseResult.Loading())
        val details = repository.getCharacterDetails(id)
        val speciesDetails = repository.getSpeciesDetails(id)

        val result = characterDetailsMapper.mapResult(details, speciesDetails)
        emit(result)
    }.catch {
        Timber.e(it)
        it.printStackTrace()
        emit(BaseResult.Failure(Errors.UnknownError))
    }
}