package com.example.testapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testapplication.data.database.entities.Tasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    // Insert Task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(task: Tasks): Long

    // Update Task
    @Transaction
    @Query("UPDATE tasks SET isCompleted = 1 WHERE id = :id")
    suspend fun setAsDone(id: Int)

    @Transaction
    @Query("UPDATE tasks SET isCompleted = 0")
    fun resetWeek()

    // GET
    @Query("SELECT * FROM tasks WHERE repeatingDay LIKE '%' || :day || '%'")
    fun getDailyTasks(day: String): Flow<List<Tasks>>

    @Query("SELECT * FROM tasks WHERE id = :idTask")
    fun getTask(idTask: Int): Flow<List<Tasks>>

    // DELETE
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query("DELETE FROM tasks where id = :idTask")
    suspend fun deleteTask(idTask: Int)
}