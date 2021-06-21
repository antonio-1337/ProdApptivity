package com.example.testapplication.ui.main.taskManager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class TaskManagerFragment() : Fragment() {

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

        binding.fab.setOnClickListener {
            //TODO: Navigate to create new task fragment
            Log.i("TaskManagerFragment", "FAB clicked.")

            val action = TaskManagerFragmentDirections.actionTaskManagerFragmentToCreateTaskFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = view.findViewById(R.id.recycler_view)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        val adapter = RecyclerAdapter()
        recyclerview.adapter = adapter


        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            // Update the cached copy of the words in the adapter.
            //tasks.let { adapter.submitList(it) }
            tasks.let {
                adapter.setnotes(it)
            }
        }
    }

    /*
    private fun generateDummyList(size: Int): List<Tasks>{
        val list = ArrayList<Tasks>()

        for(i in 0 until size){
            val drawable = when(i % 2){
                0 -> R.drawable.task_todo_check
                1 -> R.drawable.task_done_check
                else -> R.drawable.task_done_check
            }
            val item = Tasks(i+1,"","Task $i+1", "tomato",0,"2;3",drawable)
            list += item
        }
        return  list
    }*/

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