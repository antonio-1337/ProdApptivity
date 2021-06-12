package com.example.testapplication.ui.main.taskManager

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.repository.UserRepository
import java.util.*

class TaskManagerViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // Instantiate Database
    //val tasksDatabase = TasksDatabase.getDatabase()

    // Instantiate all the calendar-related variable that we need
    val calendar = Calendar.getInstance()
    val currentDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK)
    var selectedDay: Int = currentDayOfTheWeek
        set(value) {
            Log.i("TaskManagerViewModel", "Giorno settato: $value")
            field = value
        }

}