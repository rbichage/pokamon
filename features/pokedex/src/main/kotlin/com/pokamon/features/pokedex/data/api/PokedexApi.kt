package com.pokamon.features.pokedex.data.api

import com.pokamon.features.pokedex.data.model.CharacterDetailsResponse
import com.pokamon.features.pokedex.data.model.CharactersResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {
    @GET("pokemon")
    suspend fun getAllCharacters(
        @Query("limit") limit: String = "100",
        @Query("offset") offset: String = "0"
    ) : ApiResult<CharactersResponse, Any>

    @GET("pokemon/{id}")
    suspend fun getCharacterDetails(
        @Path("id") id: String
    ) : ApiResult<CharacterDetailsResponse, Any>

    @GET("pokemon-species/{id}")
    suspend fun getSpeciesDetails(
        @Path("id") speciesId: String
    ): ApiResult<SpeciesResponse, Any>
}