package com.example.testapplication.ui.main.createTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import kotlinx.coroutines.launch
import utils.TimerTypes

class CreateTaskViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var createTaskListener: CreateTaskListener? = null

    var taskName: String = ""
    var taskDescription: String = ""
    var taskRepeatingDays: List<String> = listOf(String())
    var taskTimerType: String = TimerTypes.basic.value

    fun addTask() = viewModelScope.launch {

        createTaskListener?.onStarted()

        when {
            taskName.isEmpty() -> createTaskListener?.onError("Please insert a name for your task")
            taskDescription.isEmpty() -> createTaskListener?.onError("Please insert a description for your task")
            else -> {
                var task: Tasks
                for (day in taskRepeatingDays) {
                    //create new Task object
                    task = Tasks(
                        0,
                        taskName,
                        taskDescription,
                        0,
                        day,
                        taskTimerType,
                        false
                    )
                    //save the new task to room database
                    userRepository.saveTask(task)
                }

                createTaskListener?.onSuccess("Task created successfully")
            }
        }

    }
}