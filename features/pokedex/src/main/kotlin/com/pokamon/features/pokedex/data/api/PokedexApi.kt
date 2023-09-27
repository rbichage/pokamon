package com.pokamon.features.pokedex.data.api

import com.pokamon.features.pokedex.data.model.PokemonDetailsResponse
import com.pokamon.features.pokedex.data.model.PokemonsResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {
    @GET("pokemon")
    suspend fun getAllPokemons(
        @Query("limit") limit: String = "100",
        @Query("offset") offset: String = "0"
    ) : ApiResult<PokemonsResponse, Any>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: String
    ) : ApiResult<PokemonDetailsResponse, Any>

    @GET("pokemon-species/{id}")
    suspend fun getSpeciesDetails(
        @Path("id") speciesId: String
    ): ApiResult<SpeciesResponse, Any>
}