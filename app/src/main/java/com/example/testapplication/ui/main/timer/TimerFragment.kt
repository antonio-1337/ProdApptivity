package com.example.testapplication.ui.main.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentTimerBinding
import com.example.testapplication.ui.main.taskManager.TaskSelectedDialogFragmentArgs
import com.example.testapplication.ui.main.timer.timerModes.TimerInterface
import java.util.concurrent.TimeUnit

class TimerFragment : Fragment() {

    private lateinit var viewModel: TimerViewModel

    private lateinit var binding: FragmentTimerBinding

    private lateinit var hoursPicker: NumberPicker
    private lateinit var minutesPicker: NumberPicker
    private lateinit var secondsPicker: NumberPicker

    // All the arguments from the navigation actions are stored here
    val args: TimerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Set the viewModel to the fragment
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)

        // Setup binding object and inflate the fragment xml
        binding = FragmentTimerBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        // Setup the NumberPickers to display
        numberPickerSetUp()

        // Setup the passed arguments from Navigation to the ViewModel
        argsSetup()

        // Observe the button status from the viewModel to update the UI
        viewModel.buttonStatus.observe(viewLifecycleOwner, { timerState ->
            when (timerState) {
                TimerInterface.Companion.TimerState.RUNNING_FOCUS -> runningUIManager()
                TimerInterface.Companion.TimerState.STOPPED -> stoppedUIManager()
                TimerInterface.Companion.TimerState.PAUSED -> pausedUIManager()
                TimerInterface.Companion.TimerState.RUNNING_PAUSE -> {
                    // TODO: change the progress bar color
                    runningUIManager()
                    Toast.makeText(context, "This is the pause!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return binding.root
    }

    /**
     * Pass the arguments from the Navigation component to the ViewModel
     * and set up everything according to the arguments
     */
    private fun argsSetup(){

        binding.textViewTaskName.text = args.taskName

        when(args.timerMode){
            "BASIC" -> viewModel.timerMode = TimerViewModel.TimerMode.BASIC
            "INCREMENTAL" -> viewModel.timerMode = TimerViewModel.TimerMode.INCREMENTAL
            "DECREMENTAL" -> viewModel.timerMode = TimerViewModel.TimerMode.DECREMENTAL
            "POMODORO" -> viewModel.timerMode = TimerViewModel.TimerMode.POMODORO
        }
    }

    /**
     * Set up the numberPicker View with the correct boundaries and behaviour
     */
    private fun numberPickerSetUp() {
        hoursPicker = binding.numPickerHours
        minutesPicker = binding.numPickerMinutes
        secondsPicker = binding.numPickerSeconds

        // Set boundaries for the various fields
        hoursPicker.maxValue = 23
        hoursPicker.minValue = 0

        minutesPicker.maxValue = 59
        minutesPicker.minValue = 0

        secondsPicker.maxValue = 59
        secondsPicker.minValue = 0

        // Set the pickers listeners
        hoursPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            viewModel.hours = newVal
        }

        minutesPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            viewModel.minutes = newVal
        }

        secondsPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            viewModel.seconds = newVal
        }
    }

    /**
     * Set up the hint String
     */
    private fun hintStringSetUp() {
        val timeInMinutes = TimeUnit.MILLISECONDS.toMinutes(viewModel.totalTime)
        binding.textViewHint.text =
            String.format(resources.getString(R.string.total_1_d_minutes), timeInMinutes)
    }

    /**
     * Manages the UI when the state of the timer is RUNNING
     */
    private fun runningUIManager() {
        binding.numPicker.visibility = View.GONE
        binding.fabStop.visibility = View.GONE
        binding.textViewHint.visibility = View.VISIBLE
        binding.textViewTimer.visibility = View.VISIBLE

        binding.buttonSetAsDone.isEnabled = false
        binding.fabSetMode.isEnabled = false

        binding.fabPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_pause_button))

        hintStringSetUp()
    }

    /**
     * Manages the UI when the state of the timer is STOPPED
     */
    private fun stoppedUIManager() {
        binding.numPicker.visibility = View.VISIBLE
        binding.fabStop.visibility = View.GONE
        binding.textViewHint.visibility = View.GONE
        binding.textViewTimer.visibility = View.GONE

        binding.buttonSetAsDone.isEnabled = true
        binding.fabSetMode.isEnabled = true

        binding.fabPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_play_button))
    }

    /**
     * Manages the UI when the state of the timer is PAUSED
     */
    private fun pausedUIManager() {
        binding.fabStop.visibility = View.VISIBLE
        binding.fabPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_play_button))
    }
}