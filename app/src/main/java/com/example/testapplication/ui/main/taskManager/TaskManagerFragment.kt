package com.example.testapplication.ui.main.taskManager

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapplication.R

class TaskManagerFragment : Fragment() {

    companion object {
        fun newInstance() = TaskManagerFragment()
    }

    private lateinit var viewModel: TaskManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_manager_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskManagerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}