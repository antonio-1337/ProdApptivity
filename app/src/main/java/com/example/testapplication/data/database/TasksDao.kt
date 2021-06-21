package com.example.testapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.data.database.entities.Tasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    //Insert/Update Task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(task: Tasks): Long

    //GET
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Tasks>>
    @Query("SELECT * FROM tasks WHERE repeatingDays LIKE '%' || :day || '%'")
    fun getDailyTasks(day: String): Flow<List<Tasks>>
    @Query("SELECT * FROM tasks WHERE id = :idTask")
    fun getTask(idTask: Int): LiveData<Tasks>

    //DELETE
    @Query("DELETE FROM tasks")
    fun deleteAllTasks()
    @Query("DELETE FROM tasks where id = :idTask")
    fun deleteAllTasks(idTask:Int)
}