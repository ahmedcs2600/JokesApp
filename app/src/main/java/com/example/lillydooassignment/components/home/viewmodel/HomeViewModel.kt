package com.example.lillydooassignment.components.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DataState
import com.example.domain.interactor.FetchRandomJokesUsecase
import com.example.domain.models.Joke
import com.example.lillydooassignment.components.home.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mFetchRandomJokesUsecase: FetchRandomJokesUsecase) :
    ViewModel() {

    private val _uiState = MutableLiveData<HomeUiState>()
    val uiState: LiveData<HomeUiState> get() = _uiState

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> get() = _jokes

    init {
        _uiState.value = LoadingState
        getRandomJokes()
    }

    private fun getRandomJokes() {
        viewModelScope.launch {
            mFetchRandomJokesUsecase.invoke().collect { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        _uiState.postValue(ErrorState(dataState.message))
                    }
                    is DataState.Success -> {
                        _uiState.postValue(ContentState)
                        val jokes = dataState.data
                        _jokes.postValue(jokes)
                    }
                }
            }
        }
    }

    fun refreshJokes() {
        _uiState.value = RefreshState
        getRandomJokes()
    }
}