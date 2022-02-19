package com.example.cache.manager.repository

import com.example.cache.manager.manager.DataStoreManager
import com.example.domain.repository.VisitInfoRepository
import javax.inject.Inject

class VisitInfoRepositoryImpl @Inject constructor(private val dataStoreManager: DataStoreManager):
    VisitInfoRepository {
    override suspend fun increaseVisitCount() {
        dataStoreManager.increaseVisitCount()
    }

    override suspend fun retrieveVisitCount(): Int {
        return dataStoreManager.retrieveVisitCount()
    }
}