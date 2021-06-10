package com.example.testapplication.ui.main.createTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.databinding.CreateTaskFragmentBinding

class CreateTaskFragment : Fragment() {

    private lateinit var viewModel: CreateTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Set the viewModel to the fragment
        viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)

        // Setup binding object and inflate the fragment xml
        val binding = CreateTaskFragmentBinding.inflate(inflater, container, false)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // This makes LiveData update the UI correctly
        binding.lifecycleOwner = this

        return binding.root
    }

}