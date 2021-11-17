package com.example.carstestapp.di

import com.example.carstestapp.api.GitHackApi
import com.example.carstestapp.model.repositories.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCarRepository(api: GitHackApi): CarRepository = CarRepository(api)
}