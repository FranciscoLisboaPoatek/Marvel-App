package com.example.marvel_app.feature_character.di

import com.example.marvel_app.feature_character.data.repository.CharacterRepositoryImpl
import com.example.marvel_app.feature_character.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ):CharacterRepository

}