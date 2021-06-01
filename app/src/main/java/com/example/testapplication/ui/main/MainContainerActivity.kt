package com.example.testapplication.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.testapplication.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import utils.toast

class MainContainerActivity : AppCompatActivity() {

    lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)

        toolbar = findViewById(R.id.app_header)

        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(findViewById<NavigationView>(R.id.nav_view), navController)
        NavigationUI.setupActionBarWithNavController(this, navController, findViewById<DrawerLayout>(R.id.drawer_layout))
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragmentContainerView),
            findViewById<DrawerLayout>(R.id.drawer_layout)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.fragmentTaskManager -> {
                // Handle favorite icon press
                toast("Fragment Task pressed!")
                true
            }
            R.id.fragmentAnalytics -> {
                // Handle search icon press
                toast("Analytics pressed!")
                true
            }
            R.id.action_logout -> {
                // Handle more item (inside overflow menu) press
                toast("log out pressed!")
                true
            }
            else -> false
        }

        return super.onOptionsItemSelected(item)
    }
}