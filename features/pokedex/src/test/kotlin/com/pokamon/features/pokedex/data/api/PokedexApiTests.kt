package com.pokamon.features.pokedex.data.api

import com.hannesdorfmann.instantiator.InstantiatorConfig
import com.hannesdorfmann.instantiator.instance
import com.pokamon.features.pokedex.data.model.PokemonDetailsResponse
import com.pokamon.features.pokedex.data.model.PokemonsResponse
import com.pokamon.features.pokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import com.slack.eithernet.response
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.net.HttpURLConnection
import kotlin.test.assertEquals

class PokedexApiTests {
    private val mockWebServer = MockWebServer()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var api: PokedexApi

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()

    private val moshi: Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val config = InstantiatorConfig(useDefaultArguments = false, useNull = false)

    private val charactersResponse = instance<PokemonsResponse>(config).copy(
        results = List(2) {
            instance(config)
        }
    )

    private val characterDetailsResponse = instance<PokemonDetailsResponse>(config)
    private val speciesDetailsResponse = instance<SpeciesResponse>(config)

    @Before
    fun setup() {
        mockWebServer.start()
        api = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(ApiResultConverterFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create()

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }

    @Test
    fun `test getting characters is successful`() = runTest {
        val code = HttpURLConnection.HTTP_OK

        val adapter = moshi.adapter(PokemonsResponse::class.java)

        val json = adapter.toJson(charactersResponse)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(json)
        )

        val response = api.getAllPokemons()
        val success = response as? ApiResult.Success

        assert(success is ApiResult.Success)
        assertEquals(code, success?.response()?.code)
        assertEquals(charactersResponse.results.size, success?.value?.results?.size)

    }

    @Test
    fun `test network request returns a http error code`() = runTest {
        val code = HttpURLConnection.HTTP_NOT_FOUND

        val adapter = moshi.adapter(PokemonsResponse::class.java)


        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody("Not found")
        )

        val response = api.getAllPokemons()
        val success = response as? ApiResult.Failure

        assert(success is ApiResult.Failure)
        assertEquals(code, success?.response()?.code)
    }

    @Test
    fun `test getting character details is successful`() = runTest {
        val code = HttpURLConnection.HTTP_OK

        val adapter = moshi.adapter(PokemonDetailsResponse::class.java)

        val json = adapter.toJson(characterDetailsResponse)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(json)
        )

        val response = api.getPokemonDetails("1")
        val success = response as? ApiResult.Success

        assert(success is ApiResult.Success)
        assertEquals(code, success?.response()?.code)
        assertEquals(characterDetailsResponse.abilities.size, success?.value?.abilities?.size)
        assertEquals(characterDetailsResponse.forms.size, success?.value?.forms?.size)
        assertEquals(characterDetailsResponse.name, success?.value?.name)
    }

    @Test
    fun `test getting species details is successful`() = runTest {
        val code = HttpURLConnection.HTTP_OK

        val adapter = moshi.adapter(SpeciesResponse::class.java)

        val json = adapter.toJson(speciesDetailsResponse)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(json)
        )

        val response = api.getSpeciesDetails("1")
        val success = response as? ApiResult.Success

        assert(success is ApiResult.Success)
        assertEquals(code, success?.response()?.code)
        assertEquals(speciesDetailsResponse.name, success?.value?.name)
        assertEquals(speciesDetailsResponse.color.name, success?.value?.color?.name)
        assertEquals(speciesDetailsResponse.flavorTextEntries.size, success?.value?.flavorTextEntries?.size)

    }
}