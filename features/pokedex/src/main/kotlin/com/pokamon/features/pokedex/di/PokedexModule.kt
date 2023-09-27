package com.pokamon.features.pokedex.di

import com.pokamon.features.pokedex.data.api.PokedexApi
import com.pokamon.features.pokedex.data.repository.PokedexRepository
import com.pokamon.features.pokedex.data.repository.PokedexRepositoryImpl
import com.pokamon.features.pokedex.domain.mapper.AllPokemonMapper
import com.pokamon.features.pokedex.domain.mapper.AllPokemonMapperImpl
import com.pokamon.features.pokedex.domain.mapper.CharacterDetailsMapper
import com.pokamon.features.pokedex.domain.mapper.CharacterDetailsMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokedexModule {
    @Provides
    @Singleton
    fun providePokedexApi(
        builder: Retrofit.Builder
    ): PokedexApi {
        return builder
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePokedexRepository(
        pokedexRepositoryImpl: PokedexRepositoryImpl
    ): PokedexRepository = pokedexRepositoryImpl

    @Provides
    @Singleton
    fun provideCharactersMapper(
        charactersMapperImpl: AllPokemonMapperImpl
    ): AllPokemonMapper = charactersMapperImpl

    @Provides
    @Singleton
    fun provideCharacterDetailsMapper(
        characterDetailsMapperImpl: CharacterDetailsMapperImpl
    ): CharacterDetailsMapper = characterDetailsMapperImpl
}