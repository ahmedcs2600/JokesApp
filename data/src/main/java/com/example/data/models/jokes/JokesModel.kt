package com.example.data.models.jokes

import com.google.gson.annotations.SerializedName

data class JokesModel(
	@field:SerializedName("category") val category: String,
	@field:SerializedName("type") val type: String,
	@field:SerializedName("setup") val setup: String,
	@field:SerializedName("delivery") val delivery: String,
	@field:SerializedName("flags") val flags: Flags,
	@field:SerializedName("id") val id: Int,
	@field:SerializedName("safe") val safe: Boolean,
	@field:SerializedName("lang") val lang: String
)