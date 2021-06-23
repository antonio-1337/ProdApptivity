package com.example.testapplication.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*


/*
@Entity(foreignKeys = [ForeignKey(entity = Tasks::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("task_id"),
    onDelete = CASCADE)]
)*/
@Entity
data class TaskStatus(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo
    val task_id: Int,

    @ColumnInfo
    val date: String,

    @ColumnInfo
    val isCompleted: Boolean
)