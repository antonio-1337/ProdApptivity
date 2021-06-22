package com.example.testapplication.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    //@ColumnInfo
    //val userID: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val description: String,

    @ColumnInfo
    val timeSpent: Long,

    @ColumnInfo
    val repeatingDays: String,

    //TODO change type to boolean and manage img source in front end based on value
    @ColumnInfo
    val status_imageSource: Int

    //val timerType: ??? TODO: Implement timer type
)
