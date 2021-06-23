package com.example.testapplication.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.testapplication.data.database.entities.TaskStatus
import com.example.testapplication.data.database.entities.Tasks

data class TaskAndStatus(
    @Embedded val task:Tasks,

    @Relation(
        parentColumn = "id",
        entityColumn = "task_id"
    )
    val status:TaskStatus

)