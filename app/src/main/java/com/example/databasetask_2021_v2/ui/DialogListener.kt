package com.example.databasetask_2021_v2.ui

import com.example.databasetask_2021_v2.repository.room.Dog

interface DialogListener {

    fun save(name: String, age: Int, breed: String)

    fun update(dog: Dog)

}