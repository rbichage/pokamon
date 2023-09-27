package com.pokamon.features.pokedex.domain.usecase

import app.cash.turbine.test
import com.hannesdorfmann.instantiator.InstantiatorConfig
import com.hannesdorfmann.instantiator.instance
import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.data.model.CharacterDetailsResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.pokamon.features.pokedex.data.repository.PokedexRepository
import com.pokamon.features.pokedex.domain.mapper.CharacterDetailsMapperImpl
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
import kotlin.random.Random

class GetCharacterDetailsTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val repository: PokedexRepository = mockk(relaxed = true)
    private val characterDetailsMapper = CharacterDetailsMapperImpl()

    private lateinit var getCharacterDetails: GetCharacterDetails

    private val config = InstantiatorConfig(useDefaultArguments = false, useNull = false)

    private val details = instance<CharacterDetailsResponse>(config)
    private val speciesResponse = instance<SpeciesResponse>(config)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher = dispatcher)
        getCharacterDetails = GetCharacterDetails(
            repository, characterDetailsMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getting details and species is successful`() = runTest {
        val id = Random.nextInt().toString()

        coEvery {
            repository.getCharacterDetails(id)
        } returns ApiResult.success(details)

        coEvery {
            repository.getSpeciesDetails(id)
        } returns ApiResult.success(speciesResponse)

        getCharacterDetails.execute(id).test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val success = result as? BaseResult.Success

            val data = success?.data

            assert(success is BaseResult.Success)
            assert(data?.abilities?.size == details.abilities.size)
            assert(data?.types?.size == details.types.size)
            assert(data?.stats?.size == details.stats.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getting details and species returns a http error`() = runTest {
        val id = Random.nextInt().toString()

        coEvery {
            repository.getCharacterDetails(id)
        } returns ApiResult.success(details)

        coEvery {
            repository.getSpeciesDetails(id)
        } returns ApiResult.httpFailure(
            code = HttpURLConnection.HTTP_NOT_FOUND,
            error = "Not found"
        )

        getCharacterDetails.execute(id).test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val error = result as? BaseResult.Failure
            val httpError = error?.error as? GetCharacterDetails.Errors.HttpError


            assert(error is BaseResult.Failure)
            assert(httpError is GetCharacterDetails.Errors.HttpError)
            assert(httpError?.message == "Not found")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getting details and species returns a network error`() = runTest {
        val id = Random.nextInt().toString()
        val exception = IOException("Unable to connect")

        coEvery {
            repository.getCharacterDetails(id)
        } returns  ApiResult.networkFailure(exception)

        coEvery {
            repository.getSpeciesDetails(id)
        } returns ApiResult.success(speciesResponse)

        getCharacterDetails.execute(id).test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val error = result as? BaseResult.Failure
            val httpError = error?.error as? GetCharacterDetails.Errors.NetworkError


            assert(error is BaseResult.Failure)
            assert(httpError is GetCharacterDetails.Errors.NetworkError)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getting details and species returns an unknown error`() = runTest {
        val id = Random.nextInt().toString()
        val exception = IllegalStateException("Unable to convert types")

        coEvery {
            repository.getCharacterDetails(id)
        } returns  ApiResult.unknownFailure(exception)

        coEvery {
            repository.getSpeciesDetails(id)
        } returns ApiResult.success(speciesResponse)

        getCharacterDetails.execute(id).test {
            val first = this.awaitItem()
            assert(first is BaseResult.Loading)

            val result = this.awaitItem()

            val error = result as? BaseResult.Failure
            val httpError = error?.error as? GetCharacterDetails.Errors.UnknownError


            assert(error is BaseResult.Failure)
            assert(httpError is GetCharacterDetails.Errors.UnknownError)

            cancelAndIgnoreRemainingEvents()
        }
    }
}