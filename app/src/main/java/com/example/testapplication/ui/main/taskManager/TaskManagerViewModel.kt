package com.example.testapplication.ui.main.taskManager

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

class TaskManagerViewModel : ViewModel() {

    val calendar = Calendar.getInstance()
    val currentDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK)
    var selectedDay: Int = currentDayOfTheWeek
        set(value) {
            Log.i("TaskManagerViewModel", "Giorno settato: $value")
            field = value
        }

}