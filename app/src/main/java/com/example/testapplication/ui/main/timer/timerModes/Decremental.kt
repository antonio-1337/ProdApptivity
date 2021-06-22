package com.example.testapplication.ui.main.timer.timerModes

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * A decremental timer that contracts its length every time a session finish.
 *
 * @param length the length at which the timer must operate initially.
 * @param decrement the decrement that must be subtracted to the length each time a session finish.
 */
class Decremental(
    override var length: Long,
    private val decrement: Long
) : TimerInterface {

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
//                Log.i(
//                    "Decremental",
//                    "Time left: $millisUntilFinished which corresponds to ${_timeLeft.value}"
//                )
            }

            // When the timer is over, set timeLeft to 0
            override fun onFinish() {
                _timeLeft.value = 0

                Log.i("Decremental", "Timer completed!")

                cycle()
            }
        }
    }

    // Called when the session ends.
    // Updates the length of the timer to the next increment.
    fun cycle() {
        _state.value = TimerInterface.Companion.TimerState.PAUSED

        if (length <= decrement){
            stop()
        } else{
            length -= decrement
            pauseTime = length
            timer.cancel()
            _timeLeft.value = length
        }
    }

    // Start the timer
    override fun start() {
        _state.value = TimerInterface.Companion.TimerState.RUNNING_FOCUS
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