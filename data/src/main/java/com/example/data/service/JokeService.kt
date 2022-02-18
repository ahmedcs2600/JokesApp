package com.example.data.service

import com.example.data.core.ApiResponse
import com.example.data.models.jokes.JokesResponse
import retrofit2.http.GET

interface JokeService {
    @GET("joke/Any?type=twopart&amount=10")
    suspend fun getRandomJokes(): ApiResponse<JokesResponse>
}