package com.bangnv.roomdatabase.ui

import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.bangnv.roomdatabase.R
import com.bangnv.roomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        setupNavController()
        setupActionBar()
        setupActionBarColor()
    }

    // Initialize binding for Activity layout
    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Setup NavController by retrieving NavHostFragment
    private fun setupNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    // Setup ActionBar with NavController
    private fun setupActionBar() {
        setupActionBarWithNavController(navController)
    }

    // Handle navigation when the "Up" button is pressed
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    // Set ActionBar based on light/dark mode
    private fun setupActionBarColor() {
        val isDarkMode =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        if (supportActionBar != null) {
            val actionBarColor = if (isDarkMode) {
                getColor(R.color.my_dark_primary)
            } else {
                getColor(R.color.my_light_primary)
            }
            supportActionBar?.setBackgroundDrawable(ColorDrawable(actionBarColor))
        }
    }
}