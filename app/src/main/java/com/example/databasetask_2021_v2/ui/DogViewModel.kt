package com.example.databasetask_2021_v2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databasetask_2021_v2.repository.DogRepository
import com.example.databasetask_2021_v2.repository.room.Dog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class DogViewModel(private var _repository: DogRepository) : ViewModel() {

    private val sortbyFlow = MutableStateFlow("age")
    val repository get() = _repository

    val _dogs = sortbyFlow.flatMapLatest {
        repository.getAll(it)
    }
    val dogs get() = _dogs

    fun getAllSortBy(sortBy: String) {
        this.sortbyFlow.value = sortBy
    }

    fun insert(name: String, age: Int, breed: String) {
        val dog = Dog(0, name, age, breed)
        viewModelScope.launch { repository.save(dog) }
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
    }

    private fun <T> Flow<T>.asLiveDataFlow() =
            shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}
