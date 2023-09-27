package com.pokamon.features.pokedex.data.repository

import com.pokamon.features.pokedex.data.api.PokedexApi
import com.pokamon.features.pokedex.data.model.PokemonDetailsResponse
import com.pokamon.features.pokedex.data.model.PokemonsResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val pokedexApi: PokedexApi
) : PokedexRepository {
    override suspend fun getAllCharacters(): ApiResult<PokemonsResponse, Any> {
        return pokedexApi.getAllPokemons()
    }

    override suspend fun getCharacterDetails(id: String): ApiResult<PokemonDetailsResponse, Any> {
        return pokedexApi.getPokemonDetails(id)
    }

    override suspend fun getSpeciesDetails(id: String): ApiResult<SpeciesResponse, Any> {
        return pokedexApi.getSpeciesDetails(id)
    }
}