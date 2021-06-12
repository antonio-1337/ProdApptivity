package com.example.testapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.data.database.entities.Tasks

@Dao
interface TasksDao {

    //Insert/Update Task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(task: Tasks): Long

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Tasks>

    @Query("SELECT * FROM tasks WHERE repeatingDays LIKE '%' || :day || '%'")
    fun getDailyTasks(day: String): List<Tasks>
}