package com.example.domain.interactor

import com.example.domain.repository.VisitInfoRepository
import javax.inject.Inject

class FetchVisitCountUsecase @Inject constructor(private val repository: VisitInfoRepository) {
    suspend operator fun invoke() = repository.retrieveVisitCount()
}