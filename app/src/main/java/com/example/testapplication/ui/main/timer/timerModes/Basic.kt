package com.example.testapplication.ui.main.timer.timerModes

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Basic timer.
// Should do what a basic timer does.
class Basic(private val length: Long) : TimerInterface {

    // How much time is left
    private var _timeLeft = MutableLiveData<Long>()
    override val timeLeft: LiveData<Long>
        get() = _timeLeft

    // The actual state of the timer, starts STOPPED
    override var state = TimerInterface.Companion.TimerState.STOPPED

    // Initialization of the timer object
    override var timer: CountDownTimer =
        object : CountDownTimer(length + 999, TimerInterface.ONE_DECASECOND) {

            // Update the timeLeft value every one second
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = millisUntilFinished
                Log.i("BasicTimer", "Time left: $millisUntilFinished")
            }

            // When the timer is over, set timeLeft to 0
            override fun onFinish() {
                _timeLeft.value = 0
                Log.i("BasicTimer", "Timer completed!")
            }

        }

    // Start the timer
    override fun start() {
        state = TimerInterface.Companion.TimerState.RUNNING_FOCUS
        timer.start()
    }

    // Stop the timer
    override fun stop() {
        state = TimerInterface.Companion.TimerState.STOPPED
        timer.cancel()
    }

}