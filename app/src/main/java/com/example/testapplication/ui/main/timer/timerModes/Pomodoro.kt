package com.example.testapplication.ui.main.timer.timerModes

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Basic timer.
// Should do what a basic timer does.
class Pomodoro(
    override var length: Long,
    private val pauseLength: Long
) : TimerInterface {

    // Stores the original Pomodoro length passed in the constructor
    private val pomodoroLength = length

    // Tracker for the previous state
    private var wasRunningFocus: Boolean = false

    override var totalTime: Long = 0L

    // How much time is left
    private var _timeLeft = MutableLiveData<Long>()
    override val timeLeft: LiveData<Long>
        get() = _timeLeft

    // The actual state of the timer, starts STOPPED
    private var _state =
        MutableLiveData(TimerInterface.Companion.TimerState.STOPPED)
    override val state: LiveData<TimerInterface.Companion.TimerState>
        get() = _state

    // Initialization of the timer object
    override var timer: CountDownTimer = initialize(length)

    private fun initialize(length: Long): CountDownTimer {
        return object : CountDownTimer(length, TimerInterface.ONE_DECASECOND) {

            // Update the timeLeft value every one second
            override fun onTick(millisUntilFinished: Long) {
                val remains = millisUntilFinished % 1000
                _timeLeft.value = millisUntilFinished - remains + 1000
                totalTime += TimerInterface.ONE_DECASECOND
            }

            // When the timer is over, set timeLeft to 0
            override fun onFinish() {
                _timeLeft.value = 0
                cycle()
            }
        }
    }

    // Called when the session ends.
    // Set up everything for the next cycle, updating the status based on the previous one.
    private fun cycle(){
        if (state.value == TimerInterface.Companion.TimerState.RUNNING_FOCUS){
            wasRunningFocus = true
            pauseTime = pauseLength
            length = pauseLength
        } else{
            wasRunningFocus = false
            pauseTime = pomodoroLength
            length = pomodoroLength
        }

        timer.cancel()
        _timeLeft.value = pauseTime
        _state.value = TimerInterface.Companion.TimerState.PAUSED
    }

    // Start the timer
    override fun start() {
        if (wasRunningFocus){
            _state.value = TimerInterface.Companion.TimerState.RUNNING_PAUSE
        } else {
            _state.value = TimerInterface.Companion.TimerState.RUNNING_FOCUS
        }
        timer.start()
    }

    // Stop the timer
    override fun stop() {
        _state.value = TimerInterface.Companion.TimerState.STOPPED
        _timeLeft.value = 0L
        timer.cancel()
    }

    // Pause the timer
    private var pauseTime: Long = 0
    override fun pause() {
        _state.value = TimerInterface.Companion.TimerState.PAUSED
        pauseTime = _timeLeft.value!!
        timer.cancel()
        _timeLeft.value = pauseTime
    }

    // Resumes the timer
    override fun resume() {
        timer = initialize(pauseTime)
        start()
    }
}