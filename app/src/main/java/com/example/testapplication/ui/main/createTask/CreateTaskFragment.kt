package com.example.testapplication.ui.main.createTask

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testapplication.R
import com.example.testapplication.databinding.CreateTaskFragmentBinding
import com.example.testapplication.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateTaskFragment : Fragment() {

    // Get the ViewModel from Koin
    private val viewModel: CreateTaskViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val currday: String = arguments?.get("day_today") as String
        // Set the viewModel to the fragment
        //viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)

        // Setup binding object and inflate the fragment xml
        val binding = CreateTaskFragmentBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        binding.buttonSaveTask.setOnClickListener {
            viewModel.addTask(currday)

            //go back to task manager fragment
            val action = CreateTaskFragmentDirections.actionCreateTaskFragmentToTaskManagerFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        val button = view.findViewById<Button>(R.id.buttonSaveTask)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        */

    }

}