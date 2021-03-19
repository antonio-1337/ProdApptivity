package com.example.testapplication.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityLoginBinding
import utils.hide
import utils.show
import utils.toast
import java.util.*
import kotlin.concurrent.schedule

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        val viewModel: AuthViewModel by viewModels()
        viewModel.authListener = this

        val bindingModel: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        bindingModel.viewModel = viewModel

    }

    override fun onStarted() {
        //toast("Login Started")

        findViewById<ProgressBar>(R.id.progress_bar).show()


      /*  Timer().schedule(2000) {
            TODO("Do something")
        }*/

    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            findViewById<ProgressBar>(R.id.progress_bar).hide()
            toast(it)
        })
        toast("Login Success")
    }

    override fun onError(errorMsg: String) {
        findViewById<ProgressBar>(R.id.progress_bar).hide()
        toast("Login error: $errorMsg")
    }
}