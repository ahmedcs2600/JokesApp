package com.example.domain.repository

interface VisitInfoRepository {
    suspend fun increaseVisitCount()

    suspend fun retrieveVisitCount(): Int
}