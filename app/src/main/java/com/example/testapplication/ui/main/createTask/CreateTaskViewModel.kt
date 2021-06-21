package com.example.testapplication.ui.main.createTask

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.testapplication.R
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.ui.main.taskManager.TaskManagerFragmentDirections
import kotlinx.coroutines.launch
import java.util.*

class CreateTaskViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    var task_name: String = ""
    var task_description: String = ""
    var task_repeating_days: String = ""

    fun addTask(repeating_days:String) = viewModelScope.launch {

        task_repeating_days = repeating_days

        if(task_name.isNotEmpty() && task_description.isNotEmpty() && task_repeating_days.isNotEmpty()){
            val task = Tasks(0,
                task_name,
                task_description,
                0,
                task_repeating_days,
                R.drawable.task_todo_check)
            //save new task to room database
            userRepository.saveTask(task)
        }

    }
}