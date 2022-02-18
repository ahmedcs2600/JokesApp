package com.example.cache.manager

interface DataStoreManager {

    suspend fun increaseVisitCount()

    suspend fun retrieveVisitCount(): Int
}