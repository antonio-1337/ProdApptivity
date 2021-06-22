package com.example.testapplication.ui.main.taskManager

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*

class TaskManagerViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // Instantiate all the calendar-related variable that we need
    var calendar: Calendar = Calendar.getInstance()
    val currentDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK)
    var selectedDay: Int = currentDayOfTheWeek
        set(value) {
            Log.i("TaskManagerViewModel", "Giorno settato: $value")
            field = value
            dailyTasks = userRepository.getDailyTasks(value.toString()).asLiveData()
        }

    val allTasks: LiveData<List<Tasks>> = userRepository.getAllTasks().asLiveData()

    var dailyTasks: LiveData<List<Tasks>> =
        userRepository.getDailyTasks(selectedDay.toString()).asLiveData()

    fun goToCreateTaskFragment(view: View){
        val action = TaskManagerFragmentDirections.actionTaskManagerFragmentToCreateTaskFragment(selectedDay.toString())
        view.findNavController().navigate(action)
    }

}