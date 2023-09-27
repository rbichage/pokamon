package com.pokamon.features.pokedex.ui.details

import app.cash.turbine.testIn
import com.hannesdorfmann.instantiator.InstantiatorConfig
import com.hannesdorfmann.instantiator.instance
import com.pokamon.features.networking.util.BaseResult
import com.pokamon.features.pokedex.domain.model.Character
import com.pokamon.features.pokedex.domain.usecase.CharacterDetailsResult
import com.pokamon.features.pokedex.domain.usecase.GetCharacterDetails
import com.pokamon.features.pokedex.ui.listing.ErrorType
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
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CharacterDetailsViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private val getCharactersDetails = mockk<GetCharacterDetails>(relaxed = true)

    private val config = InstantiatorConfig(useDefaultArguments = false, useNull = false)

    private val details = instance<Character>(config)

    private lateinit var viewModel: CharacterDetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher = dispatcher)
        viewModel = CharacterDetailsViewModel(
            getCharacterDetails = getCharactersDetails,
            coroutineDispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getting characters is successful`() = runTest {
        val id = Random.nextInt().toString()
        val result = MutableStateFlow<CharacterDetailsResult>(
            BaseResult.Loading()
        )

        val testFlow = viewModel.uIState.testIn(this)
        coEvery {
            getCharactersDetails.execute(id)
        } returns result

        viewModel.getDetails(id)
        assertTrue(testFlow.awaitItem() is CharacterDetailsUIState.Idle)

        result.emit(
            BaseResult.Success(details)
        )

        testFlow.apply {
            assert(awaitItem() is CharacterDetailsUIState.Loading)
            val uIState = awaitItem() as? CharacterDetailsUIState.Success
            val character = uIState?.character
            assertTrue(uIState is CharacterDetailsUIState.Success)
            assertEquals(character, details)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test getting characters returns a http error`() = runTest {
        val id = Random.nextInt().toString()
        val message = "Not found"
        val result = MutableStateFlow<CharacterDetailsResult>(
            BaseResult.Loading()
        )

        val testFlow = viewModel.uIState.testIn(this)
        coEvery {
            getCharactersDetails.execute(id)
        } returns result

        viewModel.getDetails(id)
        assert(testFlow.awaitItem() is CharacterDetailsUIState.Idle)

        result.emit(
            BaseResult.Failure(GetCharacterDetails.Errors.HttpError(message))
        )

        testFlow.apply {
            assert(awaitItem() is CharacterDetailsUIState.Loading)
            val uIState = awaitItem() as? CharacterDetailsUIState.Error
            val errorType = uIState?.errorType as? ErrorType.HttpError
            assertTrue(uIState is CharacterDetailsUIState.Error)
            assertTrue(errorType is ErrorType.HttpError)
            assert(errorType.message == message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}



