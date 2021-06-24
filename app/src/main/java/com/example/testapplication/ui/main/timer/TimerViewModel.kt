package com.example.testapplication.ui.main.timer

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.ui.main.timer.timerModes.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class TimerViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    enum class TimerMode {
        BASIC, INCREMENTAL, DECREMENTAL, POMODORO
    }

    var timerMode = TimerMode.BASIC

    // Stores the time selected by the user
    var hours: Int = 0
    var minutes: Int = 0
    var seconds: Int = 0
    private var selectedTime: Long = 0L

    val length: Long
        get() = timer.length
    val totalTime: Long
        get() = timer.totalTime

    private lateinit var timer: TimerInterface
    fun isTimerInizialized(): Boolean = this::timer.isInitialized

    // The time left to be observed by the views
    val timeLeftString = MediatorLiveData<String>()
    val timeLeftPercent = MediatorLiveData<Int>()

    // Control the button status based on the timer status
    val buttonStatus = MediatorLiveData<TimerInterface.Companion.TimerState>()

    // Initialize a value for the timeLeft so that is not null when observed by the textView
    init {
        timeLeftString.addSource(MutableLiveData("00:00")) {
            timeLeftString.value = it
        }
        timeLeftPercent.addSource(MutableLiveData(0L)) {
            timeLeftPercent.value = it.toInt()
        }
        buttonStatus.addSource(MutableLiveData(TimerInterface.Companion.TimerState.STOPPED)) {
            buttonStatus.value = it
        }
    }

    fun start() {
        if (!this::timer.isInitialized || timer.state.value == TimerInterface.Companion.TimerState.STOPPED) {
            // Create the correct timer with the correct time
            selectedTime = 0L

            selectedTime += TimeUnit.HOURS.toMillis(hours.toLong())
            selectedTime += TimeUnit.MINUTES.toMillis(minutes.toLong())
            selectedTime += TimeUnit.SECONDS.toMillis(seconds.toLong())

            // If the time selected is 0 there is no reason to start the timer.
            if (selectedTime == 0L) return

            // TODO: choose the timer that the user selected
            timer = when (timerMode) {
                TimerMode.BASIC -> Basic(selectedTime)
                TimerMode.INCREMENTAL -> Incremental(selectedTime, TimeUnit.MINUTES.toMillis(1L))
                TimerMode.DECREMENTAL -> Decremental(selectedTime, TimeUnit.MINUTES.toMillis(1L))
                TimerMode.POMODORO -> Pomodoro(selectedTime, TimeUnit.MINUTES.toMillis(1L))
            }

            // Start the timer
            timer.start()

            // Add to the timeLeft MediatorLiveData the new already initialized and started timer, so the view
            // observing the timeLeft is correctly updated
            timeLeftString.addSource(timer.timeLeft) {
                val out = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(it),
                    TimeUnit.MILLISECONDS.toSeconds(it) % 60
                )
                timeLeftString.value = out
            }

            // Calculate and updates the percent of completion
            timeLeftPercent.addSource(timer.timeLeft) {
                val percent: Float = ((it.toFloat() / timer.length.toFloat()) * 100f)
                timeLeftPercent.value = percent.toInt()
            }

            // Same but with the state of the timer
            buttonStatus.addSource(timer.state) {
                buttonStatus.value = it
            }
        } else if (timer.state.value == TimerInterface.Companion.TimerState.PAUSED) {
            resume()
        } else {
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

    // Manages database operations
    var taskId: Int = 0
    @InternalCoroutinesApi
    fun setTaskAsDone(){
        MainScope().launch {
            var result: List<Tasks>
            userRepository.getTask(taskId).collect {
                result = it
            }
        }
    }
}