package com.example.testapplication.ui.main.taskManager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.example.testapplication.R
import com.example.testapplication.databinding.TaskManagerFragmentBinding
import java.util.*

class TaskManagerFragment : Fragment() {

    private lateinit var viewModel: TaskManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Set the viewModel to the fragment
        viewModel = ViewModelProvider(this).get(TaskManagerViewModel::class.java)

        // Setup binding object and inflate the fragment xml
        val binding = TaskManagerFragmentBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        // Set the radio button of the current day checked
        setRadioButtonToCurrentDay(binding)

        return binding.root
    }

    private fun setRadioButtonToCurrentDay(binding: TaskManagerFragmentBinding) {
        when (viewModel.currentDayOfTheWeek) {
            Calendar.MONDAY -> binding.radioButtonMonday.isChecked = true
            Calendar.TUESDAY -> binding.radioButtonTuesday.isChecked = true
            Calendar.WEDNESDAY -> binding.radioButtonWednesday.isChecked = true
            Calendar.THURSDAY -> binding.radioButtonThursday.isChecked = true
            Calendar.FRIDAY -> binding.radioButtonFriday.isChecked = true
            Calendar.SATURDAY -> binding.radioButtonSaturday.isChecked = true
            Calendar.SUNDAY -> binding.radioButtonSunday.isChecked = true
        }
    }

}