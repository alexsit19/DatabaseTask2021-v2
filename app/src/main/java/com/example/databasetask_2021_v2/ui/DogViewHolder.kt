package com.example.databasetask_2021_v2.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.databasetask_2021_v2.databinding.DogCardBinding
import com.example.databasetask_2021_v2.repository.room.Dog

class DogViewHolder(
    private val binding: DogCardBinding,
    private val listener: DogItemListener,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dog: Dog) {
        binding.apply {
            nameContent.text = dog.name
            ageContent.text = dog.age.toString()
            breedContent.text = dog.breed

            editIv.setOnClickListener {
                listener.update(dog)
            }
            deleteIv.setOnClickListener {
                listener.delete(dog)
            }
        }
    }
}
