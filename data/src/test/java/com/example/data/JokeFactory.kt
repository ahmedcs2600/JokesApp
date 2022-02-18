package com.example.data

import com.example.data.models.jokes.Flags
import com.example.data.models.jokes.JokesModel
import com.example.data.models.jokes.JokesResponse
import com.example.domain.models.Joke
import java.util.*
import kotlin.random.Random

object JokeFactory {

    fun generateDummyJokesModel(): JokesModel {
        val flag = Flags(
            nsfw = getRandomBoolean(),
            religious = getRandomBoolean(),
            political = getRandomBoolean(),
            racist = getRandomBoolean(),
            sexist = getRandomBoolean(),
            explicit = getRandomBoolean()
        )

        return JokesModel(
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
            flag,
            getRandomInt(),
            getRandomBoolean(),
            generateRandomString()
        )
    }

    private fun generateRandomString() = UUID.randomUUID().toString().substring(0, 15)

    private fun getRandomBoolean(): Boolean {
        return Random.nextBoolean()
    }

    private fun getRandomInt(): Int {
        return Random.nextInt()
    }

    fun dummyRandomJokes(): JokesResponse {
        return JokesResponse(
            false,
            10,
            dummyRandomJokesModel()
        )
    }

    fun dummyRandomJokesModel() = (1..5).map {
        JokesModel(
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
            Flags(
                getRandomBoolean(),
                getRandomBoolean(),
                getRandomBoolean(),
                getRandomBoolean(),
                getRandomBoolean(),
                getRandomBoolean(),
            ),
            getRandomInt(),
            getRandomBoolean(),
            generateRandomString(),
        )
    }

    fun dummyEmptyJoke(): JokesResponse {
        return JokesResponse(
            false,
            10,
            listOf()
        )
    }

    fun generateDummyJokeList() = (0..5).map {
        generateDummyJoke()
    }

    fun generateDummyJoke() : Joke {
        return Joke(
            getRandomInt(),
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
        )
    }
}