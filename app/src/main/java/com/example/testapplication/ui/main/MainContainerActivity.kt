package com.example.testapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityHomeBinding
import com.example.testapplication.databinding.ActivityMainContainerBinding
import com.example.testapplication.ui.auth.LoginActivity
import com.example.testapplication.ui.home.HomeViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import utils.ResetWeekWorker
import utils.toast
import java.util.*
import java.util.concurrent.TimeUnit

class MainContainerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // Get the ViewModel from Koin
    private val viewModel: MainContainerViewModel by viewModel()

    private lateinit var drawerLayout: DrawerLayout
    private var drawerLockMode: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main_container)

        // Crea l'oggetto Binding per l'activity mostrando anche il layout
        val binding: ActivityMainContainerBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_container)

        // Binda il layout direttamente con il ViewModel
        binding.viewModel = viewModel


        // Setup for the toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)

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

        // Add a listener for when the destination is changed to lock the drawer, preventing the drawer bug
        navController.addOnDestinationChangedListener(destinationChangedListener)

        // Set click-listeners for the drawer
        navigationView.setNavigationItemSelectedListener(this)

        // Start WorkManager
        startWorkManager()
    }

    private fun startWorkManager(){

        val calendar = Calendar.getInstance()

        val today = calendar.get(Calendar.DAY_OF_WEEK).toLong()
        val hour = calendar.get(Calendar.HOUR_OF_DAY).toLong()

        val now = TimeUnit.DAYS.toHours(today) + hour

        val delay: Long = TimeUnit.DAYS.toHours(7) - now

        val resetWeekWorkRequest =
            PeriodicWorkRequestBuilder<ResetWeekWorker>(7, TimeUnit.DAYS)
                .addTag("WeekResetter")
                .setInitialDelay(delay, TimeUnit.HOURS)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "WeekResetter",
            ExistingPeriodicWorkPolicy.KEEP,
            resetWeekWorkRequest
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav_timer -> findNavController(R.id.nav_host_fragment).navigate(R.id.timerFragment)
            R.id.nav_delete_data -> deleteAllData()
            R.id.nav_logout -> logout()
        }
        return true
    }

    private val destinationChangedListener = NavController.OnDestinationChangedListener{ controller, destination, argument ->
        if (!drawerLockMode || destination.label == "Timer"){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            drawerLockMode = true
        } else{
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            drawerLockMode = false
        }
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        // go back to login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun deleteAllData(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete all your data?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete all the user's tasks from database
                viewModel.deleteAllData()
                toast("All data deleted successfully")
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}