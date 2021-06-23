package com.example.testapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testapplication.data.database.entities.TaskStatus
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.database.relations.TaskAndStatus


@Dao
interface TasksStatusDao {

    //GET
    @Transaction
    @Query("SELECT * FROM taskstatus WHERE task_id = :idTask")
    suspend fun getTastksStatus(idTask: Int): List<TaskAndStatus>

    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(taskStatus: TaskStatus): Long

}