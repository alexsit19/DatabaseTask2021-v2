package com.example.databasetask_2021_v2.repository.room

import androidx.room.Query
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {

    @Query("SELECT * FROM dogs ORDER BY " +
            "(CASE " +
            "WHEN :sortBy='id' THEN id " +
            "WHEN :sortBy='name' THEN name " +
            "WHEN :sortBy='age' THEN age " +
            "WHEN :sortBy='breed' THEN breed " +
            "ELSE id " +
            "END)")
    fun getAll(sortBy: String): Flow<List<Dog>>

    @Query("SELECT * FROM dogs ORDER BY name")
    fun getSortByName(): Flow<List<Dog>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(dog: Dog)

    @Delete
    suspend fun delete(dog: Dog)

    @Update
    suspend fun update(dog: Dog)
}
