package com.example.databasetask_2021_v2.repository.sqlopenhelper

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.databasetask_2021_v2.repository.*

class DogDbHelper(context: Context): SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(CREATE_TABLE_SQL)
        } catch (exception: SQLException) {
            Log.e("ERROR_TAG", "Exception while trying to create database", exception)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE_SQL)
        Log.d("DEBUG", "onUpgrade called")
        onCreate(db)
    }

    companion object {

        private const val CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS $DB_TABLE_NAME " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT NOT NULL," +
                    "$AGE_COLUMN INTEGER NOT NULL," +
                    "$BREED_COLUMN TEXT NOT NULL);"
        private const val DELETE_TABLE_SQL =
            "DROP TABLE IF EXISTS $DB_TABLE_NAME"

    }

}
