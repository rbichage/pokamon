package com.pokamon.features.pokedex.domain.mapper

import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.data.model.PokemonsResponse
import com.pokamon.features.pokedex.domain.model.PokemonListing
import com.pokamon.features.pokedex.domain.model.toCharacterListing
import com.pokamon.features.pokedex.domain.usecase.GetPokemons
import com.slack.eithernet.ApiResult
import javax.inject.Inject

internal typealias GetPokemonsResult = BaseResult<List<PokemonListing>, GetPokemons.Errors>

interface AllPokemonMapper {
    fun mapResult(
        result: ApiResult<PokemonsResponse, Any>
    ): GetPokemonsResult
}

class AllPokemonMapperImpl @Inject internal constructor() : AllPokemonMapper {
    override fun mapResult(result: ApiResult<PokemonsResponse, Any>): GetPokemonsResult {
        return when (result) {
            is ApiResult.Success -> {
                val data = result.value.results
                val characters = data.toCharacterListing()
                BaseResult.Success(
                    data = characters
                )
            }

            is ApiResult.Failure.NetworkFailure -> {
                BaseResult.Failure(GetPokemons.Errors.NetworkError)
            }

            is ApiResult.Failure.UnknownFailure -> {
                result.error.printStackTrace()
                BaseResult.Failure(GetPokemons.Errors.UnknownError)
            }

            is ApiResult.Failure.HttpFailure -> {
                val message = result.error?.toString().orEmpty()
                BaseResult.Failure(
                    GetPokemons.Errors.HttpError(
                        message = message
                    )
                )
            }

            is ApiResult.Failure.ApiFailure -> {
                BaseResult.Failure(GetPokemons.Errors.UnknownError)
            }
        }
    }

}