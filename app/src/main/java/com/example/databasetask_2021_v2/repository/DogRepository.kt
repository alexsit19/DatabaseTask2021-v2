package com.example.databasetask_2021_v2.repository

import com.example.databasetask_2021_v2.repository.room.Dog
import com.example.databasetask_2021_v2.repository.room.DogsDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

typealias DogListener = (List<Dog>) -> Unit

class DogRepository(private val dogsDao: DogsDao) : DogsDao by dogsDao {

    val listeners = mutableSetOf<Dog>()

    override fun getAll(sortBy: String): Flow<List<Dog>> = dogsDao.getAll(sortBy)

    override fun getSortByName(): Flow<List<Dog>> = dogsDao.getSortByName()

    suspend fun save(dog: Dog) = dogsDao.add(dog)

    override suspend fun delete(dog: Dog) = dogsDao.delete(dog)

    override suspend fun update(dog: Dog) = dogsDao.update(dog)

    fun listenDbUpdates(): Flow<List<Dog>> = callbackFlow {
        val listener: DogListener = {
            trySend(it)
        }
        awaitClose {
        }
    }.buffer(Channel.CONFLATED)
}
