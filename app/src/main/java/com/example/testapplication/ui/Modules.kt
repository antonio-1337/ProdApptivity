package com.example.testapplication.ui

import com.example.testapplication.data.network.NetworkConnectionInterceptor
import com.example.testapplication.data.network.WebApi
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.ui.auth.AuthViewModel
import com.example.testapplication.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// Modulo Koin per le dependency injection
val appModule: Module = module {

    single { NetworkConnectionInterceptor(get()) }
    single { WebApi(get()) }
    single { UserRepository(get()) }
}

val viewModelModule: Module = module {
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}




