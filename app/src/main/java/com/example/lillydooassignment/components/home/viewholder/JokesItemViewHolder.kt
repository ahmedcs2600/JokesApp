package com.example.lillydooassignment.components.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Joke
import com.example.lillydooassignment.databinding.LayoutJokeItemBinding
import com.example.lillydooassignment.typealiases.ItemClickListener

class JokesItemViewHolder(
    private val binding: LayoutJokeItemBinding,
    private val listener: ItemClickListener<Joke>
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun inflate(parent: ViewGroup, listener: ItemClickListener<Joke>): JokesItemViewHolder {
            return JokesItemViewHolder(
                LayoutJokeItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listener
            )
        }
    }

    fun onBind(item: Joke) {
        binding.jokeCategory.text = item.category
        binding.jokeTitle.text = item.setup
        binding.jokeDescription.text = item.delivery
        binding.root.setOnClickListener {
            listener.invoke(item)
        }
    }
}