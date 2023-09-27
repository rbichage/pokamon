package com.pokamon.features.pokedex.data.repository

import com.hannesdorfmann.instantiator.instance
import com.pokamon.features.pokedex.data.api.PokedexApi
import com.pokamon.features.pokedex.data.model.CharacterDetailsResponse
import com.pokamon.features.pokedex.data.model.CharactersResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
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
import kotlin.test.assertEquals
import kotlin.test.assertIs

class PokedexRepositoryTests {

    private val dispatcher = UnconfinedTestDispatcher()
    private var pokedexApi = mockk<PokedexApi>(relaxed = true)

    private val pokedexRepository: PokedexRepository = PokedexRepositoryImpl(pokedexApi)

    private val characters = instance<CharactersResponse>()
    private val characterDetails = instance<CharacterDetailsResponse>()
    private val speciesDetails = instance<SpeciesResponse>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `a network error returns valid errors`() = runTest {

        val exception = IOException("Unable to connect")
        val result = ApiResult.networkFailure(exception)
        coEvery {
            pokedexApi.getCharacterDetails("1")
        } returns result

        val request = pokedexRepository.getCharacterDetails("1")

        val failureResult = request as? ApiResult.Failure.NetworkFailure

        assert(failureResult is ApiResult.Failure.NetworkFailure)
        assertEquals(exception.message, failureResult?.error?.message)
        assertIs<IOException>(failureResult?.error)
    }

    @Test
    fun `http error returns valid errors`() = runTest {

        val code = 404
        val message = "Not found"
        val result = ApiResult.httpFailure(
            code = code,
            error = message
        )
        coEvery {
            pokedexApi.getSpeciesDetails("1")
        } returns result

        val request = pokedexRepository.getSpeciesDetails("1")

        val failureResult = request as? ApiResult.Failure.HttpFailure

        assert(failureResult is ApiResult.Failure.HttpFailure)
        assertEquals(code, failureResult?.code)
        assertEquals(message, failureResult?.error?.toString())
    }
    @Test
    fun `getting characters returns a successful response`() = runTest {

        val result = ApiResult.success(characters)
        coEvery {
            pokedexApi.getAllCharacters()
        } returns result

        val successResult = pokedexRepository.getAllCharacters()

        val success = successResult as? ApiResult.Success

        assert(success is ApiResult.Success)
        assertEquals(characters, success?.value)
    }

    @Test
    fun `getting character details returns a successful response`() = runTest {

        val result = ApiResult.success(characterDetails)
        coEvery {
            pokedexApi.getCharacterDetails("1")
        } returns result

        val successResult = pokedexRepository.getCharacterDetails("1")

        val success = successResult as? ApiResult.Success

        assert(success is ApiResult.Success)
        assertEquals(characterDetails, success?.value)
    }

    @Test
    fun `getting species details returns a successful response`() = runTest {

        val result = ApiResult.success(speciesDetails)
        coEvery {
            pokedexApi.getSpeciesDetails("1")
        } returns result

        val successResult = pokedexRepository.getSpeciesDetails("1")

        val success = successResult as? ApiResult.Success

        assert(success is ApiResult.Success)
        assertEquals(speciesDetails, success?.value)
    }

    @Test
    fun getCharacterDetails() {
    }

    @Test
    fun getSpeciesDetails() {
    }
}