package com.example.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.JokeFactory
import com.example.data.MainCoroutinesRule
import com.example.data.StringUtils
import com.example.data.core.ApiResponse
import com.example.data.core.message
import com.example.data.mapper.JokesMapper
import com.example.data.models.jokes.JokesResponse
import com.example.data.service.JokeService
import com.example.domain.DataState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class JokesRepositoryImplTest {

    private lateinit var repository: JokesRepositoryImpl

    @MockK
    private lateinit var apiService: JokeService

    @MockK
    private lateinit var jokesMapper: JokesMapper

    @MockK
    private lateinit var stringUtils: StringUtils

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = JokesRepositoryImpl(apiService, jokesMapper, stringUtils)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test fetchRandomJokes() return lists of jokes`() = runBlocking {
        //Given
        val dummyJokes = JokeFactory.dummyRandomJokes()
        val apiResult = ApiResponse.create(200..229, Response.success(dummyJokes))

        //When
        coEvery { jokesMapper.mapFromResponse(any()) }.returns(JokeFactory.generateDummyJoke())
        coEvery { apiService.getRandomJokes() }.returns(apiResult)

        // Invoke
        val apiResponseFlow = repository.fetchRandomJokes()

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val jokeListDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(jokeListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            jokeListDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val jokeList = (jokeListDataState as DataState.Success).data
        MatcherAssert.assertThat(jokeList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(jokeList.size, CoreMatchers.`is`(dummyJokes.jokes.size))

        coVerify(exactly = 1) { apiService.getRandomJokes() }
        confirmVerified(apiService)
    }

    @Test
    fun `test fetchRandomJokes() return empty list`() = runBlocking {
        //Given
        val dummyJokes = JokeFactory.dummyEmptyJoke()
        val apiResult = ApiResponse.create(200..229, Response.success(dummyJokes))

        //When
        coEvery { jokesMapper.mapFromResponse(any()) }.returns(JokeFactory.generateDummyJoke())
        coEvery { apiService.getRandomJokes() }.returns(apiResult)

        // Invoke
        val apiResponseFlow = repository.fetchRandomJokes()

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val jokeListDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(jokeListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            jokeListDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val jokeList = (jokeListDataState as DataState.Success).data
        MatcherAssert.assertThat(jokeList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(jokeList.size, CoreMatchers.`is`(dummyJokes.jokes.size))

        coVerify(exactly = 1) { apiService.getRandomJokes() }
        confirmVerified(apiService)
    }

    @Test
    fun `test fetchRandomJokes() throws exception`() = runBlocking {

        //Given
        val givenMessage = "Test Error Message"
        val exception = Exception(givenMessage)
        val apiResult = ApiResponse.exception<JokesResponse>(exception)

        //When
        coEvery { apiService.getRandomJokes() }.returns(apiResult)
        coEvery { stringUtils.somethingWentWrong() }.returns(givenMessage)

        // Invoke
        val apiResponseFlow = repository.fetchRandomJokes()

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseFlow.count(), CoreMatchers.`is`(1))


        val apiResponseDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.instanceOf(DataState.Error::class.java))

        val errorMessage = (apiResponseDataState as DataState.Error).message
        MatcherAssert.assertThat(errorMessage, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(errorMessage, CoreMatchers.equalTo(givenMessage))

        coVerify(atLeast = 1) { apiService.getRandomJokes() }
        confirmVerified(apiService)
    }

    @Test
    fun `test fetchRandomJokes() gives network error`() = runBlocking {
        // Given
        val givenMessage = "Test Error Message"
        val body = givenMessage.toResponseBody("text/html".toMediaTypeOrNull())
        val apiResponse = ApiResponse.error<JokesResponse>(Response.error(500, body))

        // When
        coEvery { apiService.getRandomJokes() }.returns(apiResponse)
        coEvery { stringUtils.somethingWentWrong() }.returns(givenMessage)

        // Invoke
        val apiResponseFlow = repository.fetchRandomJokes()

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseFlow.count(), CoreMatchers.`is`(1))

        val apiResponseDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponseDataState, CoreMatchers.instanceOf(DataState.Error::class.java))

        val errorMessage = (apiResponseDataState as DataState.Error).message
        MatcherAssert.assertThat(errorMessage, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(errorMessage, CoreMatchers.equalTo(apiResponse.message()))

        coVerify(atLeast = 1) { apiService.getRandomJokes() }
        confirmVerified(apiService)
    }
}