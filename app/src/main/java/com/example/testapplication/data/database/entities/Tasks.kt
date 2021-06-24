package com.example.testapplication.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val timeSpent: Long,

    @ColumnInfo
    val repeatingDay: String,

    @ColumnInfo
    val timerType: String,

    @ColumnInfo
    val isCompleted: Boolean
)
