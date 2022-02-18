package com.example.data.remote

import com.example.data.MainCoroutinesRule
import com.example.data.core.ApiResponse
import com.example.data.remote.api.ApiAbstract
import com.example.data.service.JokeService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.jvm.Throws
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

@ExperimentalCoroutinesApi
class JokesApiServiceTest : ApiAbstract<JokeService>() {
    private lateinit var apiService: JokeService


    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(JokeService::class.java)
    }

    @After
    fun tearDown() {

    }

    @Throws(IOException::class)
    @Test
    fun `test getRandomJokes() returns list of jokes`() = runBlocking {
        // Given
        enqueueResponse("/jokes_list_response.json")

        // Invoke
        val response = apiService.getRandomJokes()
        val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
        mockWebServer.takeRequest()


        // Then
        assertThat(responseBody.error, `is`(false))
        assertThat(responseBody.amount, `is`(10))
        assertThat(responseBody.jokes[0].category, `is`("Pun"))
        assertThat(responseBody.jokes[0].type, `is`("twopart"))
        assertThat(responseBody.jokes[0].setup, `is`("The past, the present and the future walk into a bar."))
        assertThat(responseBody.jokes[0].delivery, `is`("It was tense."))
        assertThat(responseBody.jokes[0].id, `is`(198))
        assertThat(responseBody.jokes[0].safe, `is`(true))
        assertThat(responseBody.jokes[0].lang, `is`("en"))
    }

}