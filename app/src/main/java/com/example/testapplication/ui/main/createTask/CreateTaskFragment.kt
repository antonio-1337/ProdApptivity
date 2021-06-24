package com.example.testapplication.ui.main.createTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentCreateTaskBinding
import com.example.testapplication.ui.main.dialogues.MultipleChoiceDialog
import com.example.testapplication.ui.main.dialogues.TimerTypeChoiceDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import utils.hide
import utils.show


class CreateTaskFragment : Fragment(), CreateTaskListener,
    MultipleChoiceDialog.SelectRepeatingDaysDialogListener,
    TimerTypeChoiceDialog.SelectTimerTypeDialogListener{

    // Get the ViewModel from Koin
    private val viewModel: CreateTaskViewModel by viewModel()
    private lateinit var repeatingDaysSwitch: SwitchCompat

    //SELECT REPEATING DAYS RESULT FROM DIALOG
    override fun onDialogPositiveClick(selected_days: List<String>) {
        //update task repeating days after closing the dialog
        viewModel.task_repeating_days = selected_days
    }
    override fun onDialogNegativeClick() {
        repeatingDaysSwitch.isChecked = false
    }

    //SELECT  TASK TIMER TYPE RESULT FROM DIALOG
    override fun onTimerTypeDialogPositiveClick(selected_timer: String) {
        viewModel.task_timer_type = selected_timer
    }

    //CREATE TASK STATUSES
    override fun onStarted() {
        view?.findViewById<ProgressBar>(R.id.createTask_progressBar)?.show()
    }
    override fun onSuccess(okMsg: String) {
        Toast.makeText(context, okMsg, Toast.LENGTH_SHORT).show()
        view?.findViewById<ProgressBar>(R.id.createTask_progressBar)?.hide()
        //go back to task manager fragment
        val action =
            CreateTaskFragmentDirections.actionCreateTaskFragmentToTaskManagerFragment()
        findNavController().navigate(action)
    }
    override fun onError(errorMsg: String) {
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        view?.findViewById<ProgressBar>(R.id.createTask_progressBar)?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //get current day param from taskManager Fragment
        val currday: List<String> = listOf(arguments?.get("day_today") as String)

        // Setup binding object and inflate the fragment xml
        val binding = FragmentCreateTaskBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        viewModel.createTaskListener = this

        viewModel.task_repeating_days = currday

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        binding.buttonSaveTask.setOnClickListener {
            viewModel.addTask()
        }

        //This opens the repeating days selection dialog
        binding.checkBoxRepeating.setOnCheckedChangeListener { buttonView, _ ->
            if (buttonView.isChecked) {
                // checked
                showSelectRepeatingDaysDialog()
            } else {
                // not checked
                println("btn is NOT checked")
            }
        }

        //This opens the timer type selection dialog
        binding.buttonSetMode.setOnClickListener{
            showSelectTimerTypeDialog()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeatingDaysSwitch = view.findViewById(R.id.checkBoxRepeating) as SwitchCompat
    }

    private fun showSelectRepeatingDaysDialog() {
        val dialog = MultipleChoiceDialog()
        dialog.setTargetFragment(this, 1)
        dialog.show(parentFragmentManager, "repeating_days")
    }

    private fun showSelectTimerTypeDialog(){
        val dialog = TimerTypeChoiceDialog()
        dialog.setTargetFragment(this,2)
        dialog.show(parentFragmentManager,"timer_type")
    }
}