package com.pokamon.features.pokedex.domain.usecase

import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.data.repository.PokedexRepository
import com.pokamon.features.pokedex.domain.mapper.AllPokemonMapper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject


class GetPokemons @Inject constructor(
    private val pokedexRepository: PokedexRepository,
    private val mapper: AllPokemonMapper
) {
    sealed interface Errors {
        data object NetworkError : Errors
        data object UnknownError : Errors
        data class HttpError(
            val message: String
        ) : Errors
    }

    fun execute() = flow {
        emit(BaseResult.Loading())
        val response = pokedexRepository.getAllCharacters()
        emit(mapper.mapResult(response))
    }.catch {
        it.printStackTrace()
        Timber.e(it)
        emit(BaseResult.Failure(Errors.UnknownError))
    }
}