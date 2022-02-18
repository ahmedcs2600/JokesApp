package com.example.data.models.jokes

import com.google.gson.annotations.SerializedName

data class JokesResponse (
	@field:SerializedName("error") val error : Boolean,
	@field:SerializedName("amount") val amount : Int,
	@field:SerializedName("jokes") val jokes : List<JokesModel>
)