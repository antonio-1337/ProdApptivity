package com.example.testapplication.ui.main.createTask

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.R
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*

class CreateTaskViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    var task_name: String = "task1"
    var task_description: String = "descrizione1"
    var task_repeating_days: String? = Calendar.TUESDAY.toString()

    fun addTask() = viewModelScope.launch {
        if(!task_name.isNullOrEmpty() && !task_description.isNullOrEmpty() && !task_repeating_days.isNullOrEmpty()){
            val temp = Tasks(0,
                task_name!!,
                task_description!!,
                0,
                task_repeating_days!!,
                R.drawable.task_todo_check)
            userRepository.saveTask(temp)
        }

    }
}