package com.example.testapplication

import android.app.Application
import com.example.testapplication.ui.appModule
import com.example.testapplication.ui.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule, viewModelModule))
        }
    }
}