package com.example.databasetask_2021_v2.ui

import com.example.databasetask_2021_v2.repository.room.Dog

interface DogItemListener {

    fun delete(dog: Dog)

    fun update(dog: Dog)
}
