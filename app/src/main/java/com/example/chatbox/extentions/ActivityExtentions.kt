package com.example.chatbox.extentions

import android.app.Activity
import android.view.View
import com.example.chatbox.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Activity.bottomNavBarVisibility(visibility: Int) {
    this.findViewById<BottomNavigationView>(R.id.bottomNavBar).visibility = visibility
}