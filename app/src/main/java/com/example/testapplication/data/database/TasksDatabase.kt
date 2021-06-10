package com.example.testapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapplication.data.database.entities.Tasks

@Database(entities = arrayOf(Tasks::class), version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TasksDatabase? = null

        fun getDatabase(context: Context): TasksDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}