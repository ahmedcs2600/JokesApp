package com.example.data.core

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class ApiResponseCallAdapter(private val responseType: Type) :
    CallAdapter<Type, Call<ApiResponse<Type>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiResponseCall(call)
    }

    internal class ApiResponseCall(private val call: Call<Type>) : Call<ApiResponse<Type>> {
        override fun clone(): Call<ApiResponse<Type>> {
            return this
        }

        override fun execute(): Response<ApiResponse<Type>> {
            throw UnsupportedOperationException("ApiResponseCallAdapter doesn't support execute")
        }

        override fun enqueue(callback: Callback<ApiResponse<Type>>) {
            call.enqueue(object : Callback<Type> {
                override fun onResponse(call: Call<Type>, response: Response<Type>) {
                    val apiResponse = ApiResponse.create(response = response)
                    callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
                }

                override fun onFailure(call: Call<Type>, t: Throwable) {
                    callback.onResponse(
                        this@ApiResponseCall, Response.success(ApiResponse.exception(t))
                    )
                }
            })
        }

        override fun isExecuted(): Boolean {
            return call.isExecuted
        }

        override fun cancel() {
            call.cancel()
        }

        override fun isCanceled(): Boolean {
            return call.isCanceled
        }

        override fun request(): Request {
            return call.request()
        }

        override fun timeout(): Timeout {
            return Timeout.NONE
        }
    }
}