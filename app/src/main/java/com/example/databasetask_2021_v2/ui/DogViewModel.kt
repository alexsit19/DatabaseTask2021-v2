package com.example.databasetask_2021_v2.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.databasetask_2021_v2.repository.DogRepository
import com.example.databasetask_2021_v2.repository.room.Dog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DogViewModel(private var _repository: DogRepository): ViewModel() {
    val repository get() = _repository
    var alldogs = repository.getAll().asLiveDataFlow()

    fun getAll() {

        viewModelScope.launch {
            repository.getAll().collect {
                Log.d("DEBUG", "LIST: ${it.toString()}")
            }
        }
    }

    fun insert(name: String, age: Int, breed: String){
        val dog = Dog(0, name, age, breed)
        viewModelScope.launch { repository.save(dog) }
        //getAll()
    }

    fun delete(dog: Dog) {
        viewModelScope.launch { repository.delete(dog) }
    }

    fun update(dog: Dog) {
        viewModelScope.launch { repository.update(dog) }
    }


    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}