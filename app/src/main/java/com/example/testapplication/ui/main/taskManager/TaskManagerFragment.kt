package com.example.testapplication.ui.main.taskManager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentTaskManagerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import utils.SwipeToDeleteCallback
import utils.snackbar
import java.util.*

class TaskManagerFragment() : Fragment(), RecyclerAdapter.OnItemClickListener {

    // Get the ViewModel from Koin
    private val viewModel: TaskManagerViewModel by viewModel()
    private lateinit var recyclerview: RecyclerView
    private lateinit var binding: FragmentTaskManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Setup binding object and inflate the fragment xml
        binding = FragmentTaskManagerBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

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
        // Set the radio button of the current day checked
        setRadioButtonToCurrentDay()

        //set on reciclerview item swiped listener
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
    private fun setRadioButtonToCurrentDay() {

        // Little trick to make the RecyclerView fill immediatly with the current day tasks
        viewModel.selectedDay = viewModel.selectedDay

        when (viewModel.selectedDay) {
            Calendar.MONDAY -> binding.radioGroup.check(R.id.radioButtonMonday)
            Calendar.TUESDAY -> binding.radioGroup.check(R.id.radioButtonTuesday)
            Calendar.WEDNESDAY -> binding.radioGroup.check(R.id.radioButtonWednesday)
            Calendar.THURSDAY -> binding.radioGroup.check(R.id.radioButtonThursday)
            Calendar.FRIDAY -> binding.radioGroup.check(R.id.radioButtonFriday)
            Calendar.SATURDAY -> binding.radioGroup.check(R.id.radioButtonSaturday)
            Calendar.SUNDAY -> binding.radioGroup.check(R.id.radioButtonSunday)
        }
    }

}