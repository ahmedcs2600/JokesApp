package com.example.domain.interactor

import com.example.domain.DataState
import com.example.domain.models.Joke
import com.example.domain.repository.JokesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchRandomJokesUsecaseTest {

    @MockK
    private lateinit var repository: JokesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test invoking FetchRandomJokesUsecase returns list of jokes`() = runBlocking {
        val usecase = FetchRandomJokesUsecase(repository)
        val list = (1..5).map { Joke(it, "type $it", "category $it", "setup $it", "delivery $it") }

        coEvery { repository.fetchRandomJokes() }.returns(flowOf(DataState.success(list)))
        val result = usecase.invoke()

        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        val joke = result.first()

        MatcherAssert.assertThat(joke, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(joke, CoreMatchers.instanceOf(DataState.Success::class.java))

        val jokesList = (joke as DataState.Success).data
        MatcherAssert.assertThat(jokesList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(jokesList.size, CoreMatchers.`is`(list.size))
    }

    @Test
    fun `test invoking FetchRandomJokesUsecase returns error`() = runBlocking {
        val usecase = FetchRandomJokesUsecase(repository)
        coEvery { repository.fetchRandomJokes() }.returns(flowOf(DataState.error("Something went wrong")))

        val result = usecase.invoke()

        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        val state = result.first()

        MatcherAssert.assertThat(state, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(state, CoreMatchers.instanceOf(DataState.Error::class.java))

        val error = (state as DataState.Error).message
        MatcherAssert.assertThat(error, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(error, CoreMatchers.`is`("Something went wrong"))
    }
}