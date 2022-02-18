package com.example.domain.repository

import com.example.domain.DataState
import com.example.domain.models.Joke
import kotlinx.coroutines.flow.Flow

interface JokesRepository {
    suspend fun fetchRandomJokes(): Flow<DataState<List<Joke>>>
}