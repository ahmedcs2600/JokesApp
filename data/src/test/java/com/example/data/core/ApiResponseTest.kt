package com.example.data.core

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun `test message is not null or empty in Exception response`() {
        val exception = Exception("message")
        val apiResponse = ApiResponse.exception<String>(exception)
        MatcherAssert.assertThat(apiResponse.message, CoreMatchers.`is`("message"))
    }

    @Test
    fun `test data is not null in success response`() {
        val response = ApiResponse.create(200..299, Response.success("test_data"))
        if (response is ApiResponse.ApiSuccessResponse) {
            MatcherAssert.assertThat(response.data, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(response.data, CoreMatchers.`is`("test_data"))
        }
    }
}