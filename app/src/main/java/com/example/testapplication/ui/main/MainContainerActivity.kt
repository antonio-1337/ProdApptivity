package com.example.testapplication.ui.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.google.android.material.navigation.NavigationView

class MainContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)

        // Setup for the toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)


        // TODO: Finire
        // Insert the user name, email and pic in the drawer
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val header = navigationView.getHeaderView(0)

        val userImage: String? = intent.getStringExtra("USER_IMAGE")
        val displayName: String? = intent.getStringExtra("DISPLAY_NAME")
        val email: String? = intent.getStringExtra("EMAIL")

        if (userImage != null && displayName != null && email != null){
            Glide.with(this).load(userImage).into(header.findViewById<ImageView>(R.id.profile_pic))
            header.findViewById<TextView>(R.id.user_name).text = displayName
            header.findViewById<TextView>(R.id.email).text = email
        }
    }
}