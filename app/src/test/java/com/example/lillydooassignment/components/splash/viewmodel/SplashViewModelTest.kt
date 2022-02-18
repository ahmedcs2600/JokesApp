package com.example.lillydooassignment.components.splash.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cache.manager.DataStoreManager
import com.example.lillydooassignment.components.home.viewmodel.MainCoroutinesRule
import com.example.lillydooassignment.utils.Event
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var dataStoreManager: DataStoreManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test when SplashViewModel load, returns Visible Count`() = runBlocking {
        //Given
        val visibleCountObserver = mockk<Observer<Event<Int>>>(relaxed = true)

        //When
        coEvery { dataStoreManager.retrieveVisitCount() }.returns(1)
        coEvery { dataStoreManager.increaseVisitCount() }.returns(Unit)

        viewModel = SplashViewModel(dataStoreManager)

        viewModel.visitCount.observeForever(visibleCountObserver)

        coVerify(exactly = 1) { dataStoreManager.retrieveVisitCount() }

        verify { visibleCountObserver.onChanged(match { it.peekContent() == 1 }) }
    }
}