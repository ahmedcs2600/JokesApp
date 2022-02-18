package com.example.lillydooassignment.components.home

sealed class HomeUiState
object LoadingState : HomeUiState()
object RefreshState : HomeUiState()
class ErrorState(val message : String) : HomeUiState()
object ContentState : HomeUiState()