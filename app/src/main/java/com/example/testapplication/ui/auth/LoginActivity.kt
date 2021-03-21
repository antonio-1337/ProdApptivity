package com.example.testapplication.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityLoginBinding
import com.example.testapplication.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import utils.hide
import utils.show
import utils.toast
import java.util.*
import kotlin.concurrent.schedule

class LoginActivity : AppCompatActivity(), AuthListener {

    private val RC_SIGN_IN: Int = 1
    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        //Google Auth Init
        createRequest()

        val viewModel: AuthViewModel by viewModels()
        viewModel.authListener = this

        val bindingModel: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        bindingModel.viewModel = viewModel

    }

    private fun createRequest() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            //doSomeOperations()
        }
    }

    fun openSomeActivityForResult() {
        val intent = Intent(this, HomeActivity::class.java)
        resultLauncher.launch(intent)
    }

    /*private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }*/


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