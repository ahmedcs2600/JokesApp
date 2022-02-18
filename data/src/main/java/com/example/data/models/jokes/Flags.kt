package com.example.data.models.jokes

import com.google.gson.annotations.SerializedName

data class Flags(
    @field:SerializedName("nsfw") val nsfw: Boolean,
    @field:SerializedName("religious") val religious: Boolean,
    @field:SerializedName("political") val political: Boolean,
    @field:SerializedName("racist") val racist: Boolean,
    @field:SerializedName("sexist") val sexist: Boolean,
    @field:SerializedName("explicit") val explicit: Boolean
)