package com.example.data.mapper

import com.example.data.models.jokes.JokesModel
import com.example.domain.models.Joke
import javax.inject.Inject

class JokesMapper @Inject constructor() : Mapper<JokesModel, Joke> {
    override fun mapFromResponse(type: JokesModel): Joke {
        return Joke(
            type.id,
            type.type,
            type.category,
            type.setup,
            type.delivery,
        )
    }
}