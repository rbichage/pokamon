package com.pokamon.features.pokedex.data.repository

import com.pokamon.features.pokedex.data.model.PokemonDetailsResponse
import com.pokamon.features.pokedex.data.model.PokemonsResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult

interface PokedexRepository {
    suspend fun getAllCharacters(): ApiResult<PokemonsResponse, Any>
    suspend fun getCharacterDetails(id: String): ApiResult<PokemonDetailsResponse, Any>
    suspend fun getSpeciesDetails(id: String): ApiResult<SpeciesResponse, Any>
}