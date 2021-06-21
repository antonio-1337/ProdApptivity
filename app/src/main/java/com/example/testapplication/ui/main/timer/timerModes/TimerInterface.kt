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

    val length: Long
    val timeLeft: LiveData<Long>
    val state: LiveData<TimerState>
    var timer: CountDownTimer

    // TODO: Make it a LiveData to update the UI asap
    var totalTime: Long

    fun start()
    fun stop()

    fun pause()
    fun resume()
}