package com.example.databasetask_2021_v2.repository.room

import androidx.core.app.RemoteInput
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {

    @Query("SELECT * FROM dogs")
    fun getAll(): Flow<List<Dog>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(dog: Dog)

    @Delete
    suspend fun delete(dog: Dog)

    @Update
    suspend fun update(dog: Dog)

}