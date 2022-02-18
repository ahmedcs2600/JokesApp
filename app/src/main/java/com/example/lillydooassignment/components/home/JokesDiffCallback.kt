package com.example.lillydooassignment.components.home

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.Joke

class JokesDiffCallback(private val prevItems : List<Joke>, private val nextItems : List<Joke>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return prevItems.size
    }

    override fun getNewListSize(): Int {
        return nextItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return prevItems[oldItemPosition].id == nextItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return prevItems[oldItemPosition] == nextItems[newItemPosition]
    }
}