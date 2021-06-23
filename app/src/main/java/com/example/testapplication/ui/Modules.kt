package com.example.testapplication.ui

import com.example.testapplication.data.database.AppDatabase
import com.example.testapplication.data.network.NetworkConnectionInterceptor
import com.example.testapplication.data.network.WebApi
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.ui.auth.AuthViewModel
import com.example.testapplication.ui.home.HomeViewModel
import com.example.testapplication.ui.main.createTask.CreateTaskViewModel
import com.example.testapplication.ui.main.taskManager.TaskManagerViewModel
import com.example.testapplication.ui.main.timer.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// Modulo Koin per le dependency injection
val appModule: Module = module {
    single { NetworkConnectionInterceptor(get()) }
    single { WebApi(get()) }
    single { AppDatabase(get()) }
    single { UserRepository(get(), get()) }
}

val viewModelModule: Module = module {
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { TaskManagerViewModel(get()) }
    viewModel { CreateTaskViewModel(get()) }
    viewModel { TimerViewModel(get()) }
}




