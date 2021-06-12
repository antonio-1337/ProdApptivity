package com.example.testapplication.ui.main.taskManager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapplication.databinding.TaskManagerFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TaskManagerFragment() : Fragment() {

    // Get the ViewModel from Koin
    private val viewModel: TaskManagerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Set the viewModel to the fragment
        //viewModel = ViewModelProvider(this).get(TaskManagerViewModel::class.java)

        // Setup binding object and inflate the fragment xml
        val binding = TaskManagerFragmentBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        // Set the radio button of the current day checked
        setRadioButtonToCurrentDay(binding)

        binding.fab.setOnClickListener {
            //TODO: Navigate to create new task fragment
            Log.i("TaskManagerFragment", "FAB clicked.")

            val action = TaskManagerFragmentDirections.actionTaskManagerFragmentToCreateTaskFragment()
            findNavController().navigate(action)
        }

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