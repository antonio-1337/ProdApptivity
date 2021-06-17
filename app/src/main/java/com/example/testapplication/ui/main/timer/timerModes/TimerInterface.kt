package com.example.testapplication.ui.main.timer.timerModes

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Interface that all custom timers must implement
interface TimerInterface {
    companion object{
        const val ONE_SECOND : Long = 1000L
        const val ONE_DECASECOND: Long = 100L
        enum class TimerState {
            STOPPED, PAUSED, RUNNING_FOCUS, RUNNING_PAUSE
        }
    }

    val timeLeft: LiveData<Long>
    var state: TimerState
    var timer: CountDownTimer

    fun start()
    fun stop()
}