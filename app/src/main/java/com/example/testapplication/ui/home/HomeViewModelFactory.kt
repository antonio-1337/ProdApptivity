package com.example.testapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
        private  val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(userRepository) as T
    }
}