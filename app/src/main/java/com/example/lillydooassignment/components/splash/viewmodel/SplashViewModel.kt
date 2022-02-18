package com.example.lillydooassignment.components.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cache.manager.DataStoreManager
import com.example.lillydooassignment.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    private val _visitCount = MutableLiveData<Event<Int>>()
    val visitCount: LiveData<Event<Int>> get() = _visitCount

    init {
        retrieveCount()
    }

    private fun retrieveCount() {
        viewModelScope.launch {
            val count = dataStoreManager.retrieveVisitCount()
            _visitCount.postValue(Event(count))
            dataStoreManager.increaseVisitCount()
        }
    }
}