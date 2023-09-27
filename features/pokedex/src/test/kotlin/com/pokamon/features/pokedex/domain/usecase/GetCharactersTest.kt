package com.pokamon.features.pokedex.domain.usecase

import app.cash.turbine.test
import com.hannesdorfmann.instantiator.InstantiatorConfig
import com.hannesdorfmann.instantiator.instance
import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.data.model.PokemonsResponse
import com.pokamon.features.pokedex.data.repository.PokedexRepository
import com.pokamon.features.pokedex.domain.mapper.AllPokemonMapperImpl
import com.slack.eithernet.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.HttpURLConnection
import kotlin.test.assertTrue

class GetCharactersTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private val repository: PokedexRepository = mockk(relaxed = true)
    private val mapper = AllPokemonMapperImpl()

    private lateinit var getCharacters: GetPokemons

    private val config = InstantiatorConfig(useDefaultArguments = false, useNull = false)

    private val characters = instance<PokemonsResponse>(config)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher = dispatcher)
        getCharacters = GetPokemons(
            repository, mapper
        )
    }

    @Test
    fun `getting characters is successful`() = runTest {

        coEvery {
            repository.getAllCharacters()
        } returns ApiResult.success(characters)


        getCharacters.execute().test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val success = result as? BaseResult.Success

            val data = success?.data

            val random = data?.random()

            assert(success is BaseResult.Success)
            assert(data?.size == characters.results.size)
            assertTrue(characters.results.any { it.name == random?.name })
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getting characters returns a http error`() = runTest {

        coEvery {
            repository.getAllCharacters()
        } returns ApiResult.httpFailure(
            code = HttpURLConnection.HTTP_NOT_FOUND,
            error = "Not found"
        )


        getCharacters.execute().test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val failure = result as? BaseResult.Failure

            val error = failure?.error as? GetPokemons.Errors.HttpError


            assert(failure is BaseResult.Failure)
            assert(error is GetPokemons.Errors.HttpError)
            assert(error?.message == "Not found")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getting characters returns a network error`() = runTest {

        coEvery {
            repository.getAllCharacters()
        } returns ApiResult.networkFailure(
            IOException("Unable to connect")
        )


        getCharacters.execute().test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val failure = result as? BaseResult.Failure

            val error = failure?.error as? GetPokemons.Errors.NetworkError

            assert(failure is BaseResult.Failure)
            assert(error is GetPokemons.Errors.NetworkError)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getting characters returns an unknown error`() = runTest {

        coEvery {
            repository.getAllCharacters()
        } returns ApiResult.unknownFailure(
            IllegalStateException("Oops")
        )


        getCharacters.execute().test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val failure = result as? BaseResult.Failure

            val error = failure?.error as? GetPokemons.Errors.UnknownError

            assert(failure is BaseResult.Failure)
            assert(error is GetPokemons.Errors.UnknownError)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}