package com.example.databasetask_2021_v2

import android.app.Application
import android.util.Log
import com.example.databasetask_2021_v2.repository.DogRepository
import com.example.databasetask_2021_v2.repository.room.DogRoomDatabase
import com.example.databasetask_2021_v2.repository.sqlopenhelper.DogDatabaseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DogApplication : Application() {

    init {
        instance = this
    }

    fun getRepository(isRoom: Boolean = true): DogRepository {
        return if (isRoom) {
            Log.d("DEBUG", "ROOM")
            val applicationScope = CoroutineScope(SupervisorJob())
            val database by lazy { DogRoomDatabase.getDatabase(this, applicationScope) }
            val repository by lazy { DogRepository(database.dogsDao()) }
            repository
        } else {
            Log.d("DEBUG", "CURSOR")
            val database = DogDatabaseHandler.dogsDao(this)
            val repository by lazy { DogRepository(DogDatabaseHandler.dogsDao(this)) }
            repository
        }
    }

    companion object {
        private var instance: DogApplication? = null
        fun getInstance(): DogApplication? {
            return instance
        }
    }
}
