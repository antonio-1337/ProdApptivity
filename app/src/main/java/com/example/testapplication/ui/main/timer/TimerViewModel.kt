package com.example.testapplication.ui.main.timer

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.testapplication.ui.main.timer.timerModes.Basic
import com.example.testapplication.ui.main.timer.timerModes.TimerInterface
import java.util.concurrent.TimeUnit

class TimerViewModel : ViewModel() {

    // ATTENTION: The logic behind the pause and resume cycle are to be implemented here in the viewModel
    // to keep the various timer classes simple
    private var timer: TimerInterface = Basic(10000L)
    val timeLeft: LiveData<String> = Transformations.map(timer.timeLeft) { tLeft ->
        var out = "ER:RO"
        if (tLeft != null) {
            out = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(tLeft) % 60,
                TimeUnit.MILLISECONDS.toSeconds(tLeft) % 60
            )
        }
        return@map out
    }

    fun start() {
        timer.start()
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