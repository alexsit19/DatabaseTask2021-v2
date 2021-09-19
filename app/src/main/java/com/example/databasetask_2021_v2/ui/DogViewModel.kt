package com.example.databasetask_2021_v2.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.databasetask_2021_v2.repository.DogRepository
import com.example.databasetask_2021_v2.repository.room.Dog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DogViewModel(private var _repository: DogRepository): ViewModel() {

    private val sortbyFlow = MutableStateFlow("age")
    val repository get() = _repository

    val _dogs = sortbyFlow.flatMapLatest {
        repository.getAll(it)
    }
    val dogs get() = _dogs

    init {
//        val list = listOf(Dog(name="a", age=1, breed ="a"), Dog(name="b", age=2, breed="b"))
//        _dogs.value = list

    }





    fun getAllSortBy(sortBy: String) {
        val list = listOf(Dog(name="c", age=3, breed ="c"), Dog(name="d", age=4, breed="d"), Dog(0, "g", 4, "5"))


        this.sortbyFlow.value = sortBy

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

    fun getDataSortedBy(sortBy: String) {
        getAllSortBy(sortBy)
        this.sortbyFlow.value = sortBy
        //getAll(sortBy)

    }

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}