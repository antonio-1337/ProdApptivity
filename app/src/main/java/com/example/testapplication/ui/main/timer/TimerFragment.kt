package com.example.testapplication.ui.main.timer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.R
import com.example.testapplication.databinding.TimerFragmentBinding
import com.example.testapplication.ui.main.timer.timerModes.TimerInterface

class TimerFragment : Fragment() {

    private lateinit var viewModel: TimerViewModel

    private lateinit var hoursPicker: NumberPicker
    private lateinit var minutesPicker: NumberPicker
    private lateinit var secondsPicker: NumberPicker


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Set the viewModel to the fragment
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)

        // Setup binding object and inflate the fragment xml
        val binding = TimerFragmentBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        // Setup the NumberPickers to display
        numberPickerSetUp(binding)

        // Observe the button status from the viewModel to update the UI
        viewModel.buttonStatus.observe(viewLifecycleOwner, Observer { timerState ->
            Log.i("TimerFragment", "Timer state: $timerState")
            when (timerState) {
                TimerInterface.Companion.TimerState.RUNNING_FOCUS -> {
                    binding.numPicker.visibility = View.GONE
                    binding.fabStop.visibility = View.GONE
                    binding.textViewTimer.visibility = View.VISIBLE
                    binding.buttonSetAsDone.isEnabled = false
                    binding.fabPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_pause_circle_filled_24))
                }
                TimerInterface.Companion.TimerState.STOPPED -> {
                    binding.numPicker.visibility = View.VISIBLE
                    binding.textViewTimer.visibility = View.GONE
                    binding.fabStop.visibility = View.GONE
                    binding.buttonSetAsDone.isEnabled = true
                    binding.fabPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_play_arrow_24))
                }
                TimerInterface.Companion.TimerState.PAUSED -> {
                    binding.fabPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_play_arrow_24))
                    binding.fabStop.visibility = View.VISIBLE
                }
//                TimerInterface.Companion.TimerState.RUNNING_PAUSE -> TODO()
            }
        })

        return binding.root
    }

    fun numberPickerSetUp(binding: TimerFragmentBinding) {
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
        hoursPicker.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            viewModel.hours = newVal
        })

        minutesPicker.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            viewModel.minutes = newVal
        })

        secondsPicker.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            viewModel.seconds = newVal
        })
    }
}