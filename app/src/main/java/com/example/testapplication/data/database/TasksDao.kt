package com.example.testapplication.data.database

import androidx.room.Dao
import androidx.room.Query
import com.example.testapplication.data.database.entities.Tasks

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Tasks>

//    @Query("SELECT * FROM tasks WHERE repeatingDays LIKE :day")
//    fun getDailyTasks(day: Int): List<Tasks>
}