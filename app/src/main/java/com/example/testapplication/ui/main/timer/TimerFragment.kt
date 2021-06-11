package com.example.testapplication.ui.main.timer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapplication.R
import com.example.testapplication.databinding.TaskManagerFragmentBinding
import com.example.testapplication.databinding.TimerFragmentBinding

class TimerFragment : Fragment() {

    private lateinit var viewModel: TimerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Set the viewModel to the fragment
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)

        // Setup binding object and inflate the fragment xml
        val binding = TimerFragmentBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        return binding.root
    }
}