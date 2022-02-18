package com.example.domain.interactor

import com.example.domain.repository.JokesRepository
import javax.inject.Inject

class FetchRandomJokesUsecase @Inject constructor(private val repository: JokesRepository) {
    suspend operator fun invoke() = repository.fetchRandomJokes()
}