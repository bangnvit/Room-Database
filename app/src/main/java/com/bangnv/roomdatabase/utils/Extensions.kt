package com.bangnv.roomdatabase.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

// Toast for Fragment
fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

// Toast for Activity
fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

// Extension function to handle WindowInsets for edge-to-edge experience
fun View.applyWindowInsetsWithBottomNavigation() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        // Keep left + right + bottom padding from XML;
        // top = systemBars.top = height of the notification bar, which varies(thay đổi) by screen resolution and device model.
        v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, v.paddingBottom)
        insets
    }
}

fun View.applyWindowInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        // Keep left + right from XML;
        // top = systemBars.top = height of the notification bar, which varies based on screen resolution and device model.
        // bot = systemBars.bottom = height of the navigation bar, which varies based on screen resolution and device model.
        v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, systemBars.bottom)
        insets
    }
}
