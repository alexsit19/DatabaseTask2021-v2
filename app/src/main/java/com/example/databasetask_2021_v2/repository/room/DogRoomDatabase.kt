package com.example.databasetask_2021_v2.repository.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.databasetask_2021_v2.DogApplication
import com.example.databasetask_2021_v2.repository.DB_VERSION
import com.example.databasetask_2021_v2.repository.DB_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Dog::class], version = DB_VERSION, exportSchema = false)
public abstract class DogRoomDatabase : RoomDatabase() {

    abstract fun dogsDao(): DogsDao

    companion object {

        @Volatile
        private var INSTANCE: DogRoomDatabase? = null

        fun getDatabase(
            context: DogApplication?,
            scope: CoroutineScope
        ): DogRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context!!.applicationContext,
                    DogRoomDatabase::class.java,
                    DB_NAME
                )
                    .addCallback(DogsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DogsDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.dogsDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(dogsDao: DogsDao) {
            // Delete all content here.
            val names = arrayListOf(
                "Tuzik", "Bobik", "Artemon",
                "FatDog", "FunnyDog", "MegaDog",
                "SuperDog", "ProstoDog", "CrazyDog"
            )
            val ages = arrayListOf(2, 3, 6, 1, 8, 4, 4, 6, 8)
            val breeds = arrayListOf(
                "basset", "boxer", "cavador",
                "dachshund", "puli", "terrypoo",
                "springador", "shorkie", "komondor"
            )
            for (i in 0..8) {
                dogsDao.add(Dog(0, names[i], ages[i], breeds[i]))
            }
        }
    }
}
