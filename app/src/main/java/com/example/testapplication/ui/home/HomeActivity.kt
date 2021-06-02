package com.example.testapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testapplication.R
import com.example.testapplication.data.network.responses.GetQuoteResponse
import com.example.testapplication.databinding.ActivityHomeBinding
import com.example.testapplication.ui.auth.LoginActivity
import com.example.testapplication.ui.main.MainContainerActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import utils.hide
import utils.show
import utils.toast

class HomeActivity : AppCompatActivity(), HomeListener {

    private val home_vm: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)

        //val viewModel = home_vm
        val bindingModel: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        bindingModel.viewModel = home_vm
        home_vm.homeListener = this

        bindingModel.buttonStart.setOnClickListener { gotoMainpage() }

        //get random quote of the day
        home_vm.getRandomQuote()

        val googleAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (googleAccount != null){
            findViewById<TextView>(R.id.name).text = getString(R.string.hello_name, googleAccount.givenName)
            //findViewById<TextView>(R.id.textView2).text = google_account.email
        }
    }

    fun logOffUser() {
        FirebaseAuth.getInstance().signOut()
        // go back to login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun gotoMainpage() {
        //Intent(view.context, MainContainerActivity::class.java).also {
        //    view.context.startActivity(it)
        //}

        val intent = Intent(this, MainContainerActivity::class.java)
        startActivity(intent)
    }

    override fun onStarted() {
        findViewById<ProgressBar>(R.id.homeProgressBar).show()
    }

    override fun onSuccess(response: GetQuoteResponse) {

        //Picasso.get().load(response).into(findViewById<ImageView>(R.id.))
        findViewById<TextView>(R.id.quote_text).text = response.q
        findViewById<TextView>(R.id.quote_author).text = response.a

        findViewById<ProgressBar>(R.id.homeProgressBar).hide()

    }

    override fun onError(errorMsg: String) {
        findViewById<ProgressBar>(R.id.homeProgressBar).hide()
        toast(errorMsg)
    }
}