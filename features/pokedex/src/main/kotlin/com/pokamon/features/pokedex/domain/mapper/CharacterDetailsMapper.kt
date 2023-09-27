package com.pokamon.features.pokedex.domain.mapper

import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.networking.util.createImageUrl
import com.pokamon.features.pokedex.data.model.CharacterDetailsResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.pokamon.features.pokedex.domain.model.Character
import com.pokamon.features.pokedex.domain.model.getIdFromUrl
import com.pokamon.features.pokedex.domain.usecase.CharacterDetailsResult
import com.pokamon.features.pokedex.domain.usecase.GetCharacterDetails
import com.slack.eithernet.ApiResult
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

interface CharacterDetailsMapper {
    fun mapResult(
        characterDetailsResult: ApiResult<CharacterDetailsResponse, Any>,
        speciesDetailsResult: ApiResult<SpeciesResponse, Any>,
    ): CharacterDetailsResult
}

class CharacterDetailsMapperImpl @Inject internal constructor() : CharacterDetailsMapper {
    override fun mapResult(
        characterDetailsResult: ApiResult<CharacterDetailsResponse, Any>,
        speciesDetailsResult: ApiResult<SpeciesResponse, Any>
    ): CharacterDetailsResult {
        return when (characterDetailsResult) {
            is ApiResult.Success -> {
                when (speciesDetailsResult) {
                    is ApiResult.Success -> {
                        val species = speciesDetailsResult.value
                        val details = characterDetailsResult.value


                        val id = details.species.url.getIdFromUrl()
                        val mapped = Character(
                            id = id,
                            name = details.species.name,
                            stats = details.stats.map {
                                Pair(it.stat.name, it.baseStat)
                            },
                            imageUrl = createImageUrl(id, highQuality = true),
                            about = species
                                .flavorTextEntries
                                .firstOrNull {
                                    it.language
                                        .name
                                        .contentEquals(Locale.ENGLISH.language, true)
                                }
                                ?.flavorText
                                ?.replace("\n", " ")
                                .orEmpty(),
                            height = "${details.height/10.0} Metres",
                            weight = "${(details.weight/10.0)} Kilograms",
                            abilities = details.abilities.map { ability ->
                                Pair(ability.ability.name, ability.isHidden)
                            }.reversed(),
                            types = details.types.map {
                                it.type.name
                            },
                            characterColor = mapToColor(species.color.name)
                        )

                        BaseResult.Success(mapped)
                    }

                    is ApiResult.Failure.NetworkFailure, is ApiResult.Failure.ApiFailure -> {
                        BaseResult.Failure(GetCharacterDetails.Errors.NetworkError)
                    }

                    is ApiResult.Failure.UnknownFailure -> {
                        speciesDetailsResult.error.printStackTrace()
                        BaseResult.Failure(GetCharacterDetails.Errors.UnknownError)
                    }

                    is ApiResult.Failure.HttpFailure -> {
                        BaseResult.Failure(
                            GetCharacterDetails.Errors.HttpError(
                                speciesDetailsResult.error.toString()
                            )
                        )
                    }
                }
            }

            is ApiResult.Failure.NetworkFailure -> {
                BaseResult.Failure(GetCharacterDetails.Errors.NetworkError)
            }

            is ApiResult.Failure.ApiFailure -> {
                Timber.e("api failure ${characterDetailsResult.error?.toString()}")
                BaseResult.Failure(GetCharacterDetails.Errors.NetworkError)
            }

            is ApiResult.Failure.UnknownFailure -> {
                characterDetailsResult.error.printStackTrace()
                BaseResult.Failure(GetCharacterDetails.Errors.UnknownError)
            }

            is ApiResult.Failure.HttpFailure -> {
                BaseResult.Failure(
                    GetCharacterDetails.Errors.HttpError(
                        characterDetailsResult.error.toString()
                    )
                )
            }
        }
    }

}