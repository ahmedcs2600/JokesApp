package com.example.data.repository

import androidx.annotation.WorkerThread
import com.example.data.StringUtils
import com.example.data.core.message
import com.example.data.core.onErrorSuspend
import com.example.data.core.onExceptionSuspend
import com.example.data.core.onSuccessSuspend
import com.example.data.mapper.JokesMapper
import com.example.data.service.JokeService
import com.example.domain.DataState
import com.example.domain.models.Joke
import com.example.domain.repository.JokesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class JokesRepositoryImpl @Inject constructor(
    private val jokeService: JokeService,
    private val jokesMapper: JokesMapper,
    private val stringUtils: StringUtils
) : JokesRepository {

    @WorkerThread
    override suspend fun fetchRandomJokes(): Flow<DataState<List<Joke>>> {
        return flow {
            jokeService.getRandomJokes().apply {
                onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it.jokes.map { joke ->
                            jokesMapper.mapFromResponse(
                                joke
                            )
                        }))
                    }
                }.onErrorSuspend {
                    emit(DataState.error(message()))
                }.onExceptionSuspend {
                    if (this.exception is IOException) {
                        emit(DataState.error(stringUtils.noNetworkErrorMessage()))
                    } else {
                        emit(DataState.error(stringUtils.somethingWentWrong()))
                    }
                }
            }
        }
    }
}