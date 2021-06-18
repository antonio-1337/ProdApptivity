package com.example.testapplication.ui.main.timer

import android.util.Log
import androidx.lifecycle.*
import com.example.testapplication.ui.main.timer.timerModes.Basic
import com.example.testapplication.ui.main.timer.timerModes.TimerInterface
import java.util.concurrent.TimeUnit


class TimerViewModel : ViewModel() {

    // Stores the time selected by the user
    var hours: Int = 0
    var minutes: Int = 0
    var seconds: Int = 0

    private lateinit var timer: TimerInterface

    // The time left to be observed by the views
    val timeLeftString = MediatorLiveData<String>()
    val timeLeftPercent = MediatorLiveData<Int>()

    // Control the button status based on the timer status
    val buttonStatus = MediatorLiveData<TimerInterface.Companion.TimerState>()

    // Initialize a value for the timeLeft so that is not null when observed by the textView
    init {
        timeLeftString.addSource(MutableLiveData<String>("00:00")){
            timeLeftString.value = it
        }
        timeLeftPercent.addSource(MutableLiveData<Long>(0L)){
            timeLeftPercent.value = it.toInt()
        }
        buttonStatus.addSource(MutableLiveData(TimerInterface.Companion.TimerState.STOPPED)){
            buttonStatus.value = it
        }
    }

    fun start() {
        if (!this::timer.isInitialized || timer.state.value == TimerInterface.Companion.TimerState.STOPPED){
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
            timeLeftString.addSource(timer.timeLeft){
                val out = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(it),
                    TimeUnit.MILLISECONDS.toSeconds(it) % 60
                )
                timeLeftString.value = out
            }

            // Calculate and updates the percent of completion
            timeLeftPercent.addSource(timer.timeLeft){
                var percent: Float = ((it.toFloat() / time.toFloat()) * 100f)
                Log.i("TimerViewModel", "Percent: $percent, ($it / $time) * 100")
                timeLeftPercent.value = percent.toInt()
            }

            // Same but with the state of the timer
            buttonStatus.addSource(timer.state){
                buttonStatus.value = it
            }
        } else if (timer.state.value == TimerInterface.Companion.TimerState.PAUSED){
            resume()
        } else{
            pause()
        }

    }

    fun stop() {
        timer.stop()
    }

    fun pause() {
        timer.pause()
    }

    fun resume() {
        timer.resume()
    }


}