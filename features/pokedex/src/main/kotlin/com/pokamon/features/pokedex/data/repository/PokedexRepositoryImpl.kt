package com.pokamon.features.pokedex.data.repository

import com.pokamon.features.pokedex.data.api.PokedexApi
import com.pokamon.features.pokedex.data.model.CharacterDetailsResponse
import com.pokamon.features.pokedex.data.model.CharactersResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val pokedexApi: PokedexApi
) : PokedexRepository {
    override suspend fun getAllCharacters(): ApiResult<CharactersResponse, Any> {
        return pokedexApi.getAllCharacters()
    }

    override suspend fun getCharacterDetails(id: String): ApiResult<CharacterDetailsResponse, Any> {
        return pokedexApi.getCharacterDetails(id)
    }

    override suspend fun getSpeciesDetails(id: String): ApiResult<SpeciesResponse, Any> {
        return pokedexApi.getSpeciesDetails(id)
    }
}