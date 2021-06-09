package com.example.testapplication.ui.main.taskManager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapplication.R

class TaskManagerFragment : Fragment() {

    private lateinit var viewModel: TaskManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO: create everything lol
        viewModel = ViewModelProvider(this).get(TaskManagerViewModel::class.java)

        return inflater.inflate(R.layout.task_manager_fragment, container, false)
    }
}