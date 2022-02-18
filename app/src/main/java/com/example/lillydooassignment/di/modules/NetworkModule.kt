package com.example.lillydooassignment.di.modules

import com.example.data.core.ApiResponseCallAdapterFactory
import com.example.data.service.JokeService
import com.example.lillydooassignment.global.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@LoggingInterceptor interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        factory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(factory)
            addCallAdapterFactory(callAdapterFactory)
        }.build()
    }

    @Provides
    fun providesApiResponseCallAdapterFactory(factory : ApiResponseCallAdapterFactory) : CallAdapter.Factory = factory

    @Singleton
    @Provides
    fun providesJokesService(retrofit: Retrofit): JokeService = retrofit.create(JokeService::class.java)

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory = GsonConverterFactory.create()
}