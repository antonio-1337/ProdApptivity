package com.example.testapplication.ui.main.taskManager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.testapplication.R
import com.example.testapplication.databinding.FragmentTaskManagerBinding

class TaskManagerFragment : Fragment() {

    private lateinit var viewModel: TaskManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Crea l'oggetto Binding per il Fragment mostrando anche il layout
        val binding: FragmentTaskManagerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_task_manager, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskManagerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}