package com.example.testapplication.ui.main.timer

import androidx.lifecycle.*
import com.example.testapplication.ui.main.timer.timerModes.Basic
import com.example.testapplication.ui.main.timer.timerModes.TimerInterface
import java.util.concurrent.TimeUnit


class TimerViewModel : ViewModel() {

    // ATTENTION: The logic behind the pause and resume cycle are to be implemented here in the viewModel
    // to keep the various timer classes simple
    var hours: Int = 0
    var minutes: Int = 0
    var seconds: Int = 0

    // Control the button status based on the timer status
    private var _buttonStatus = MutableLiveData(TimerInterface.Companion.TimerState.STOPPED)
    val buttonStatus: LiveData<TimerInterface.Companion.TimerState>
        get() = _buttonStatus

    private lateinit var timer: TimerInterface

    // The time left to be observed by the views
    val timeLeft = MediatorLiveData<String>()

    // Initialize a value for the timeLeft so that is not null when observed by the textView
    init {
        timeLeft.addSource(MutableLiveData<String>("00:00")){
            timeLeft.value = it
        }
    }

    fun start() {
        // Create the correct timer with the correct time
        var time: Long = 0L

        time += TimeUnit.HOURS.toMillis(hours.toLong())
        time += TimeUnit.MINUTES.toMillis(minutes.toLong())
        time += TimeUnit.SECONDS.toMillis(seconds.toLong())

        timer = Basic(time)

        // Start the timer
        timer.start()

        // Add to the timeLeft MediatorLiveData the new already initialized and started timer, so the view
        // observing the timeLeft is correctly updated
        timeLeft.addSource(timer.timeLeft){
            val out = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(it),
                    TimeUnit.MILLISECONDS.toSeconds(it) % 60
                )
            timeLeft.value = out
        }

        // Change button status
        _buttonStatus.value = TimerInterface.Companion.TimerState.RUNNING_FOCUS

    }

    fun stop() {
        timer.stop()
    }

    fun pause() {
        // TODO: Implement the pause logic
    }

    fun resume() {
        // TODO: Implement the resume logic
    }


}