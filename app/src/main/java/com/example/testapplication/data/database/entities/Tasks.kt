package com.example.testapplication.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
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
    val repeatingDays: String

    //val timerType: ??? TODO: Implement timer type
)
