package com.example.testapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.R
import com.example.testapplication.data.network.responses.GetQuoteResponse
import com.example.testapplication.databinding.ActivityHomeBinding
import com.example.testapplication.ui.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import utils.hide
import utils.show
import utils.toast

class HomeActivity : AppCompatActivity(), HomeListener, KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance("home")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)

        val viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        val bindingModel: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        bindingModel.viewModel = viewModel
        viewModel.homeListener = this

        //get random quote of the day
        viewModel.getRandomQuote()

        val google_account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (google_account != null){
            findViewById<TextView>(R.id.textView).text = "Hello " + google_account.givenName + "!"
            //findViewById<TextView>(R.id.textView2).text = google_account.email
        }
    }

    fun logOffUser(view: View){
        FirebaseAuth.getInstance().signOut()
        // go back to login screen
        val intent = Intent(this, LoginActivity::class.java)
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