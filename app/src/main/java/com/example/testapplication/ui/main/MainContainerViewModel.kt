package com.example.testapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainContainerViewModel(
    private val userRepository: UserRepository
) : ViewModel()  {

    fun deleteAllData() = viewModelScope.launch {
        userRepository.deleteAllTasks()
    }
}