package com.example.databasetask_2021_v2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.databasetask_2021_v2.databinding.DogCardBinding
import com.example.databasetask_2021_v2.repository.room.Dog
import com.example.databasetask_2021_v2.ui.DogItemListener
import com.example.databasetask_2021_v2.ui.DogViewHolder

class DogListAdapter(
    private val listener: DogItemListener
    ) : ListAdapter<Dog, DogViewHolder>(DogsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DogCardBinding.inflate(layoutInflater, parent,false)
        return DogViewHolder(binding, listener, parent.context)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        holder.bind(dog)
    }
}

class DogsComparator: DiffUtil.ItemCallback<Dog>() {
    override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem == newItem
    }
}