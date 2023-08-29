package com.example.chatbox.ui

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chatbox.R
import com.example.chatbox.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setup Bottom Navigation
//        binding.bottomNavBar.setupWithNavController(binding.navHost.findNavController())

        setUpBottomNavBar()
    }

    private fun setUpBottomNavBar() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigation = binding.bottomNavBar
        bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED

        bottomNavigation.itemTextAppearanceInactive = R.style.BottomNavigationTextStyle
        bottomNavigation.itemTextAppearanceActive = R.style.BottomNavigationTextStyle

        setupWithNavController(bottomNavigation, navController)

    }
}