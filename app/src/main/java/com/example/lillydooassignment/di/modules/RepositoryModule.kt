package com.example.lillydooassignment.di.modules

import com.example.domain.repository.VisitInfoRepository
import com.example.cache.manager.repository.VisitInfoRepositoryImpl
import com.example.data.repository.JokesRepositoryImpl
import com.example.domain.repository.JokesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun providesJokesRepository(jokesRepositoryImpl: JokesRepositoryImpl): JokesRepository

    @Binds
    fun providesVisitInfoRepository(visitInfoRepositoryImpl : VisitInfoRepositoryImpl) : VisitInfoRepository
}