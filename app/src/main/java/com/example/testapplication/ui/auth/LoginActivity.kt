package com.example.testapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.R
import com.example.testapplication.data.network.NetworkConnectionInterceptor
import com.example.testapplication.data.network.WebApi
import com.example.testapplication.data.repository.UserRepository
import com.example.testapplication.databinding.ActivityLoginBinding
import com.example.testapplication.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import utils.hide
import utils.show
import utils.snackbar
import utils.toast

class LoginActivity : AppCompatActivity(), AuthListener {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN: Int = 1
    private var googleSignInClient: GoogleSignInClient? = null

    private var signInWithGoogleBtn: ImageView? = null

    private var TAG = "LoginActivity"

    override fun onStart() {
        super.onStart()

        val user: FirebaseUser? = auth?.currentUser
        //sel'utente è già loggato vai alla homepage
        if(user != null)
        {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = WebApi(networkConnectionInterceptor)
        val repository = UserRepository(api)
        val factory = AuthViewModelFactory(repository)


        //init firebase Auth
        auth = FirebaseAuth.getInstance()
        //Google Auth Init
        createRequest()


        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        val bindingModel: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        bindingModel.viewModel = viewModel
        viewModel.authListener = this

    }

    private fun createRequest() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_webclient_ID))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

   /* var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            //doSomeOperations()
        }
    }

    fun openSomeActivityForResult() {
        val intent = Intent(this, HomeActivity::class.java)
        resultLauncher.launch(intent)
    }*/

    private fun signIn() {
        findViewById<ProgressBar>(R.id.progress_bar).show()
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                e.message?.let { toast(it) }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        findViewById<ProgressBar>(R.id.progress_bar).hide()
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        val user = auth?.currentUser
                        // go to home activity
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        findViewById<ProgressBar>(R.id.progress_bar).hide()
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        toast("Oops. something went wrong with the authentication")
                    }
                }
    }



    override fun onStarted() {
        //toast("Login Started")

        findViewById<ProgressBar>(R.id.progress_bar).show()


      /*  Timer().schedule(2000) {
            TODO("Do something")
        }*/

    }

    override fun onSuccess(response: String) {

        findViewById<ProgressBar>(R.id.progress_bar).hide()
        //toast("Login Success")
        findViewById<View>(R.id.root_layout).snackbar("Login Success with the message $response")
    }

    override fun onError(errorMsg: String) {
        findViewById<ProgressBar>(R.id.progress_bar).hide()
        findViewById<View>(R.id.root_layout).snackbar("Login error: $errorMsg")
        //toast("Login error: $errorMsg")
    }

    fun tryGoogleSignin(view: View) {
        signIn()
    }
}