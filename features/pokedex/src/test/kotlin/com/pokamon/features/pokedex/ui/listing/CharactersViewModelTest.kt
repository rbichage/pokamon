package com.pokamon.features.pokedex.ui.listing

import app.cash.turbine.testIn
import com.hannesdorfmann.instantiator.InstantiatorConfig
import com.hannesdorfmann.instantiator.instance
import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.domain.mapper.GetPokemonsResult
import com.pokamon.features.pokedex.domain.model.PokemonListing
import com.pokamon.features.pokedex.domain.usecase.GetPokemons
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class CharactersViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val getCharacters = mockk<GetPokemons>(relaxed = true)

    private val config = InstantiatorConfig(useDefaultArguments = false, useNull = false)
    private val characters = List(5) {
        instance<PokemonListing>(config)
    }

    private lateinit var charactersViewModel: CharactersViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        charactersViewModel = CharactersViewModel(
            getPokemons = getCharacters,
            coroutineDispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getting characters is successful`() = runTest {
        val result = MutableStateFlow<GetPokemonsResult>(
            BaseResult.Loading()
        )

        val testFlow = charactersViewModel.uiState.testIn(this)
        coEvery {
            getCharacters.execute()
        } returns result

        charactersViewModel.getCharacters()
        assert(testFlow.awaitItem() is CharactersUIState.Loading)

        result.emit(
            BaseResult.Success(characters)
        )

        testFlow.apply {
            val uIState = awaitItem() as? CharactersUIState.Success
            val items = uIState?.characters.orEmpty()
            assert(uIState is CharactersUIState.Success)
            assertTrue(items.isNotEmpty())
            assert(items.size == characters.size)
            val random = items.random()
            assertTrue(characters.any { it.name.contentEquals(random.name) })

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getting characters returns a network error`() = runTest {
        val result = MutableStateFlow<GetPokemonsResult>(
            BaseResult.Loading()
        )

        val testFlow = charactersViewModel.uiState.testIn(this)
        coEvery {
            getCharacters.execute()
        } returns result

        charactersViewModel.getCharacters()
        assert(testFlow.awaitItem() is CharactersUIState.Loading)

        result.emit(
            BaseResult.Failure(GetPokemons.Errors.NetworkError)
        )

        testFlow.apply {
            val uIState = awaitItem() as? CharactersUIState.Error
            val error = uIState?.errorType
            assert(uIState is CharactersUIState.Error)
            assert(error is ErrorType.NetworkError)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getting characters returns a http error`() = runTest {
        val message = "Not found"
        val result = MutableStateFlow<GetPokemonsResult>(
            BaseResult.Loading()
        )

        val testFlow = charactersViewModel.uiState.testIn(this)
        coEvery {
            getCharacters.execute()
        } returns result

        charactersViewModel.getCharacters()
        assert(testFlow.awaitItem() is CharactersUIState.Loading)

        result.emit(
            BaseResult.Failure(GetPokemons.Errors.HttpError(message))
        )

        testFlow.apply {
            val uIState = awaitItem() as? CharactersUIState.Error
            val error = uIState?.errorType as? ErrorType.HttpError
            assert(error is ErrorType.HttpError)
            assert(error?.message == message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}