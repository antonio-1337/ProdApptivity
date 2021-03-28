package com.example.testapplication.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.testapplication.R
import com.example.testapplication.ui.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val google_account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (google_account != null){
            findViewById<TextView>(R.id.textView).text = google_account.displayName
            findViewById<TextView>(R.id.textView2).text = google_account.email
        }
    }

    fun logOffUser(view: View){
        FirebaseAuth.getInstance().signOut()
        // go back to login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}