package com.example.testapplication.ui.main.taskManager

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class TaskManagerViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // Instantiate all the calendar-related variable that we need
    var calendar: Calendar = Calendar.getInstance()
    val currentDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK)

    private val _dailyTasks = MutableLiveData<List<Tasks>>()
    val dailyTasks: LiveData<List<Tasks>>
        get() = _dailyTasks

    var selectedDay: Int = currentDayOfTheWeek
        set(day) {
            field = day
            MainScope().launch {
                userRepository.getDailyTasks(day.toString()).collect {
                    _dailyTasks.value = it
                }
            }
        }

    fun goToCreateTaskFragment(view: View) {
        val action =
            TaskManagerFragmentDirections.actionTaskManagerFragmentToCreateTaskFragment(selectedDay.toString())
        view.findNavController().navigate(action)
    }

    fun deleteTaskByID(id:Int) = viewModelScope.launch{
        userRepository.deleteTask(id)
    }

    fun restoreDeletedTask(task: Tasks) = viewModelScope.launch {
        userRepository.saveTask(task)
    }

}