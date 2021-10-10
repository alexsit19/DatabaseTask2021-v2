package com.example.databasetask_2021_v2.repository.sqlopenhelper

import android.content.Context
import android.util.Log
import com.example.databasetask_2021_v2.repository.DB_TABLE_NAME
import com.example.databasetask_2021_v2.repository.ID_COLUMN
import com.example.databasetask_2021_v2.repository.NAME_COLUMN
import com.example.databasetask_2021_v2.repository.AGE_COLUMN
import com.example.databasetask_2021_v2.repository.BREED_COLUMN
import com.example.databasetask_2021_v2.repository.room.Dog
import com.example.databasetask_2021_v2.repository.room.DogsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

class DogDatabaseHandler(private val context: Context) : DogsDao {

    private val dogDbHelper = DogDbHelper(context)

    fun dogsDao(): DogsDao {
        return DogDatabaseHandler(context)
    }

    override fun getSortByName(): Flow<List<Dog>> {
        val list = listOf<List<Dog>>()
        return list.asFlow()
    }

    override fun getAll(sortBy: String): Flow<List<Dog>> {
        Log.d("DEBUG", "GET ALL DB HANDLER")
        return flow {
            val listOfDogs = mutableListOf<Dog>()
            val db = dogDbHelper.readableDatabase
            val data = db.rawQuery("SELECT * FROM $DB_TABLE_NAME", null)
            Log.d("DEBUG", "GET ALL DB HANDLER")
            data.use { cursor ->
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    if (cursor != null) {
                        do {
                            val id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN))
                            val name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN))
                            val age = cursor.getInt(cursor.getColumnIndex(AGE_COLUMN))
                            val breed = cursor.getString(cursor.getColumnIndex(BREED_COLUMN))
                            val dog = Dog(id, name, age, breed)
                            listOfDogs.add(dog)
                            emit(listOfDogs)
                        } while (cursor.moveToNext())
                    }
                }
            }
        }
    }

    override suspend fun add(dog: Dog) {
        val db = dogDbHelper.writableDatabase
        val queryString = "INSERT INTO $DB_TABLE_NAME " +
                "($NAME_COLUMN, $AGE_COLUMN, $BREED_COLUMN)" +
                "VALUES (\'${dog.name}\', \'${dog.age}\', \'${dog.breed}\')"
        db.execSQL(queryString)
    }

    override suspend fun delete(dog: Dog) {
    }

    override suspend fun update(dog: Dog) {
    }

    companion object {
        fun dogsDao(context: Context): DogsDao {
            return DogDatabaseHandler(context)
        }
    }
}
