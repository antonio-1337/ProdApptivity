package com.example.testapplication.ui.main.taskManager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.databinding.TaskManagerFragmentBinding
import com.example.testapplication.ui.RecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class TaskManagerFragment() : Fragment(), RecyclerAdapter.OnItemClickListener {

    // Get the ViewModel from Koin
    private val viewModel: TaskManagerViewModel by viewModel()
    private lateinit var recyclerview: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Setup binding object and inflate the fragment xml
        val binding = TaskManagerFragmentBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        // Set the radio button of the current day checked
        setRadioButtonToCurrentDay(binding)

        /*
        binding.fab.setOnClickListener {
            //TODO: Navigate to create new task fragment
            Log.i("TaskManagerFragment", "FAB clicked.")

            val action = TaskManagerFragmentDirections.actionTaskManagerFragmentToCreateTaskFragment()
            findNavController().navigate(action)
        }*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = view.findViewById(R.id.recycler_view)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        val adapter = RecyclerAdapter(this)
        recyclerview.adapter = adapter

        viewModel.dailyTasks.observe(viewLifecycleOwner) { tasks ->
            // Update the cached copy of the words in the adapter.
            //tasks.let { adapter.submitList(it) }
            tasks.let {
                adapter.setTasks(it)
            }
        }
    }

    // Navigate to the dialog passing all the arguments it needs
    override fun onItemClick(position: Int) {
        val item = viewModel.dailyTasks.value?.get(position)
        if (item != null){
            val action = TaskManagerFragmentDirections.actionTaskManagerFragmentToTaskSelectedDialogFragment()
            action.taskId = item.id
            action.taskName = item.name
            action.taskDescription = item.description
            action.timerMode = "NOT YET TIMERMODE"
            findNavController().navigate(action)
        }
    }

    // Automatically selects the current day of the week
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