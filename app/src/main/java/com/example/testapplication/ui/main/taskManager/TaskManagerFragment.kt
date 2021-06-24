package com.example.testapplication.ui.main.taskManager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentTaskManagerBinding
import com.example.testapplication.ui.RecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import utils.SwipeToDeleteCallback
import utils.snackbar
import java.util.*


class TaskManagerFragment() : Fragment(), RecyclerAdapter.OnItemClickListener {

    // Get the ViewModel from Koin
    private val viewModel: TaskManagerViewModel by viewModel()
    private lateinit var recyclerview: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Setup binding object and inflate the fragment xml
        val binding = FragmentTaskManagerBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        // Set the radio button of the current day checked
        setRadioButtonToCurrentDay(binding)

        // Little trick to make the RecyclerView fill immediatly with the current day tasks
        viewModel.selectedDay = viewModel.currentDayOfTheWeek

        return binding.root
    }

    private val adapter = RecyclerAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = view.findViewById(R.id.recycler_view)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(activity)

        recyclerview.adapter = adapter

        viewModel.dailyTasks.observe(viewLifecycleOwner) { tasks ->
            // Update the cached copy of the words in the adapter.
            //tasks.let { adapter.submitList(it) }
            tasks.let {
                adapter.setTasks(it)
            }
        }

        /*
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val note = viewModel.dailyTasks.value?.get(position)

                    if (note != null) {
                        viewModel.deleteTaskByID(note.id)
                    }

                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)*/

        val swipeHandler = object : SwipeToDeleteCallback(view.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = viewModel.dailyTasks.value?.get(viewHolder.adapterPosition)

                if (note != null) {
                    viewModel.deleteTaskByID(note.id)
                    view.snackbar("Task deleted")
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview)

    }

    // Navigate to the dialog passing all the arguments it needs
    override fun onItemClick(position: Int) {
        val item = viewModel.dailyTasks.value?.get(position)
        if (item != null) {
            val action =
                TaskManagerFragmentDirections.actionTaskManagerFragmentToTaskSelectedDialogFragment()
            action.taskId = item.id
            action.taskName = item.name
            action.taskDescription = item.description
            action.timerMode = item.timerType
            findNavController().navigate(action)
        }
    }

    // Automatically selects the current day of the week
    private fun setRadioButtonToCurrentDay(binding: FragmentTaskManagerBinding) {
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