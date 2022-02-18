package com.example.data.mapper

import com.example.data.JokeFactory
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test

class JokesMapperTest {

    private lateinit var jokesMapper : JokesMapper

    @Before
    fun setUp() {
        jokesMapper = JokesMapper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test JokesMapper return Joke`() {
        val jokeModel = JokeFactory.generateDummyJokesModel()
        val joke = jokesMapper.mapFromResponse(jokeModel)

        MatcherAssert.assertThat(joke.id,CoreMatchers.`is`(jokeModel.id))
        MatcherAssert.assertThat(jokeModel.type,CoreMatchers.`is`(jokeModel.type))
        MatcherAssert.assertThat(jokeModel.category,CoreMatchers.`is`(jokeModel.category))
        MatcherAssert.assertThat(jokeModel.setup,CoreMatchers.`is`(jokeModel.setup))
        MatcherAssert.assertThat(jokeModel.delivery,CoreMatchers.`is`(jokeModel.delivery))
    }
}