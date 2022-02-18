package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Joke(
    val id: Int,
    val type: String,
    val category: String,
    val setup: String,
    val delivery: String,
) : Parcelable