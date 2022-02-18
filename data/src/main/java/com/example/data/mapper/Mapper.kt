package com.example.data.mapper

interface Mapper<R, M> {
    fun mapFromResponse(type: R): M
}