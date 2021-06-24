package com.example.testapplication.ui.main.createTask

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testapplication.R
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.ui.home.HomeListener
import com.example.testapplication.ui.main.taskManager.TaskManagerFragmentDirections
import kotlinx.coroutines.launch
import utils.TimerTypes
import java.util.*

class CreateTaskViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var createTaskListener: CreateTaskListener? = null

    var task_name: String = ""
    var task_description: String = ""
    var task_repeating_days: String = ""
    var task_timer_type: String = TimerTypes.basic.value

    fun addTask() = viewModelScope.launch {

        createTaskListener?.onStarted()

        if(task_name.isNullOrEmpty())
            createTaskListener?.onError("Please insert a name for your task")
        else if(task_description.isNullOrEmpty())
            createTaskListener?.onError("Please insert a description for your task")
        else{
            //create new Task object
            val task = Tasks(
                0,
                task_name,
                task_description,
                0,
                task_repeating_days,
                R.drawable.task_todo_check,
                task_timer_type
            )
            //save the new task to room database
            userRepository.saveTask(task)
            createTaskListener?.onSuccess("Task created successfully")
        }

    }
}