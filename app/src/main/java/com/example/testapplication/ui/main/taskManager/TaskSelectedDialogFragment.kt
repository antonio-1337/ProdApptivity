package com.example.testapplication.ui.main.taskManager

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testapplication.R
import com.example.testapplication.databinding.DialogTaskBinding

class TaskSelectedDialogFragment() : DialogFragment() {

    // All the arguments from the navigation actions are stored here
    val args: TaskSelectedDialogFragmentArgs by navArgs()

    private lateinit var binding: DialogTaskBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Set up the binding object
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_task, null, false)

        // Setup the info to display in the dialog
        binding.textViewName.text = args.taskName
        binding.textViewDescription.text = args.taskDescription
        binding.textViewTimerMode.text = args.timerMode

        // Create the onClick listener for the "Start focusing!" button
        binding.buttonStartFocusing.setOnClickListener(View.OnClickListener {
            navigateToTimer()
        })

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // Navigate to the timer fragment
    // TODO: Passing arguments to the timer fragment
    fun navigateToTimer(){
        val action = TaskSelectedDialogFragmentDirections.actionTaskSelectedDialogFragmentToTimerFragment()
        action.taskName = args.taskName
        findNavController().navigate(action)
    }
}