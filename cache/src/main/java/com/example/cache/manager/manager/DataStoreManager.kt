package com.example.cache.manager.manager

interface DataStoreManager {

    suspend fun increaseVisitCount()

    suspend fun retrieveVisitCount(): Int
}