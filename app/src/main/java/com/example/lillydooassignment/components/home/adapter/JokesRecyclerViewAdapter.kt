package com.example.lillydooassignment.components.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Joke
import com.example.lillydooassignment.components.home.JokesDiffCallback
import com.example.lillydooassignment.components.home.viewholder.JokesItemViewHolder
import com.example.lillydooassignment.typealiases.ItemClickListener


class JokesRecyclerViewAdapter(private val listener : ItemClickListener<Joke>) : RecyclerView.Adapter<JokesItemViewHolder>() {

    private val items = ArrayList<Joke>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesItemViewHolder {
        return JokesItemViewHolder.inflate(parent,listener)
    }

    override fun onBindViewHolder(holder: JokesItemViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = items.size

    fun updateList(items : List<Joke>) {
        val diffCallback = JokesDiffCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this);
    }
}