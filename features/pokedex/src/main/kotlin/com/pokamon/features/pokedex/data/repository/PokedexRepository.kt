package com.pokamon.features.pokedex.data.repository

import com.pokamon.features.pokedex.data.model.CharacterDetailsResponse
import com.pokamon.features.pokedex.data.model.CharactersResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult

interface PokedexRepository {
    suspend fun getAllCharacters(): ApiResult<CharactersResponse, Any>
    suspend fun getCharacterDetails(id: String): ApiResult<CharacterDetailsResponse, Any>
    suspend fun getSpeciesDetails(id: String): ApiResult<SpeciesResponse, Any>
}