package com.example.testapplication.ui.main.taskManager

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
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

    var selectedDay: Int = 0
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

}