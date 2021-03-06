package com.example.testapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityLoginBinding
import com.example.testapplication.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.viewmodel.ext.android.viewModel
import utils.hide
import utils.show
import utils.snackbar
import utils.toast

//TODO Spostare la logica del Log-In nel ViewModel
class LoginActivity : AppCompatActivity(), AuthListener {

    // Ottengo il ViewModel da Koin
    private val authViewModel: AuthViewModel by viewModel()

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN: Int = 1
    private var googleSignInClient: GoogleSignInClient? = null

    private var TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inizializza Firebase Auth
        auth = FirebaseAuth.getInstance()

        //Google Auth Init
        createRequest()

        // Crea l'oggetto Binding per l'activity mostrando anche il layout
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // Binda il layout direttamente con il ViewModel
        binding.viewModel = authViewModel

        // Passa al ViewModel questa activity come authListener
        authViewModel.authListener = this

    }

    override fun onStart() {
        super.onStart()

        val user: FirebaseUser? = auth?.currentUser

        // Se l'utente ?? gi?? loggato, vai alla homepage
        if(user != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStarted() {
        // Mostra la progress bar
        findViewById<ProgressBar>(R.id.progress_bar).show()
    }


    //==========DA SPOSTARE NEL VIEWMODEL===========

    private fun createRequest() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_webclient_ID))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

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