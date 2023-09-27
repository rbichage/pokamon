package com.pokamon.features.pokedex.domain.mapper

import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.data.model.CharactersResponse
import com.pokamon.features.pokedex.domain.model.CharacterListing
import com.pokamon.features.pokedex.domain.model.toCharacterListing
import com.pokamon.features.pokedex.domain.usecase.GetCharacters
import com.slack.eithernet.ApiResult
import javax.inject.Inject

internal typealias GetCharactersResult = BaseResult<List<CharacterListing>, GetCharacters.Errors>

interface AllCharactersMapper {
    fun mapResult(
        result: ApiResult<CharactersResponse, Any>
    ): GetCharactersResult
}

class AllCharactersMapperImpl @Inject internal constructor() : AllCharactersMapper {
    override fun mapResult(result: ApiResult<CharactersResponse, Any>): GetCharactersResult {
        return when (result) {
            is ApiResult.Success -> {
                val data = result.value.results
                val characters = data.toCharacterListing()
                BaseResult.Success(
                    data = characters
                )
            }

            is ApiResult.Failure.NetworkFailure -> {
                BaseResult.Failure(GetCharacters.Errors.NetworkError)
            }

            is ApiResult.Failure.UnknownFailure -> {
                result.error.printStackTrace()
                BaseResult.Failure(GetCharacters.Errors.UnknownError)
            }

            is ApiResult.Failure.HttpFailure -> {
                val message = result.error?.toString().orEmpty()
                BaseResult.Failure(
                    GetCharacters.Errors.HttpError(
                        message = message
                    )
                )
            }

            is ApiResult.Failure.ApiFailure -> {
                BaseResult.Failure(GetCharacters.Errors.UnknownError)
            }
        }
    }

}