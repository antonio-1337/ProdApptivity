package com.example.testapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.testapplication.data.database.entities.Tasks

@Dao
interface TasksStatusDao {
    @Query("SELECT * FROM taskstatus WHERE task_id = :idTask")
    fun getTastksStatus(idTask: Int): List<Tasks>
}