package com.example.testapplication

import android.app.Application
import com.example.testapplication.data.network.NetworkConnectionInterceptor
import com.example.testapplication.data.network.WebApi
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.ui.auth.AuthViewModelFactory
import com.example.testapplication.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BaseApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))

        bind () from singleton { NetworkConnectionInterceptor(instance()) }
        bind () from singleton { WebApi(instance()) }
        bind ("repouser") from singleton { UserRepository(instance()) }

        //viewmodel factory bindings
        bind ("auth") from provider { AuthViewModelFactory(instance("repouser")) }
        bind ("home") from provider { HomeViewModelFactory(instance("repouser")) }
    }
}