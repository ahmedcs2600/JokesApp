package com.example.lillydooassignment.components.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.domain.DataState
import com.example.domain.interactor.FetchRandomJokesUsecase
import com.example.domain.models.Joke
import com.example.lillydooassignment.components.getOrAwaitValue
import com.example.lillydooassignment.components.home.ContentState
import com.example.lillydooassignment.components.home.HomeUiState
import com.example.lillydooassignment.components.home.RefreshState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import kotlin.random.Random

@RunWith(JUnit4::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var mFetchRandomJokesUsecase: FetchRandomJokesUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test jokes are fetched`() = runBlocking {
        // Given
        val dummyJokeList = generateDummyJokeList()
        val uiObserver = mockk<Observer<HomeUiState>>(relaxed = true)
        val jokeListObserver = mockk<Observer<List<Joke>>>(relaxed = true)

        // When
        coEvery { mFetchRandomJokesUsecase.invoke() }.returns(flowOf(DataState.success(dummyJokeList)))

        viewModel = HomeViewModel(mFetchRandomJokesUsecase)

        // Invoke
        viewModel.uiState.observeForever(uiObserver)
        viewModel.jokes.observeForever(jokeListObserver)


        val slots = mutableListOf<HomeUiState>()
        verify { uiObserver.onChanged(capture(slots)) }

        // Then
        coVerify(exactly = 1) { mFetchRandomJokesUsecase.invoke() }
        verify { uiObserver.onChanged(match { it == ContentState }) }

        verify { jokeListObserver.onChanged(match { it.size == dummyJokeList.size }) }
    }

    private fun generateRandomString() = UUID.randomUUID().toString().substring(0, 15)

    inline fun <reified T > LiveData<T>.captureValues(): List<T?> {
        val mockObserver = mockk<Observer<T>>()
        val list = mutableListOf<T?>()
        every { mockObserver.onChanged(captureNullable(list))} just runs
        this.observeForever(mockObserver)
        return list
    }

    private fun generateDummyJokeList() = (0..5).map {
        Joke(
            Random.nextInt(),
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
            generateRandomString(),
        )
    }
}