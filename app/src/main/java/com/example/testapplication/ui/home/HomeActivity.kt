package com.example.testapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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

// TODO spostare la logica legata all'account google nel ViewModel
class HomeActivity : AppCompatActivity(), HomeListener {

    // Ottengo il ViewModel da Koin
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crea l'oggetto Binding per l'activity mostrando anche il layout
        val binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        // Binda il layout direttamente con il ViewModel
        binding.viewModel = homeViewModel

        // Passa al ViewModel questa activity come homeListener
        homeViewModel.homeListener = this

        // Ottieni la frase del giorno
        homeViewModel.getRandomQuote()

        // Ottieni e mostra il nome dell'utente loggato
        val googleAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (googleAccount != null){
            findViewById<TextView>(R.id.name).text = getString(R.string.hello_name, googleAccount.givenName)
        }

        // Naviga verso la MainContainerActivity, vedi nel ViewModel per spiegazioni.
        homeViewModel.toMainPage.observe(this, Observer {
            val intent = Intent(this, MainContainerActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onStarted() {
        findViewById<ProgressBar>(R.id.homeProgressBar).show()
    }



    //======DA METTERE NEL VIEWMODEL======

    fun logOffUser() {
        FirebaseAuth.getInstance().signOut()
        // go back to login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
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