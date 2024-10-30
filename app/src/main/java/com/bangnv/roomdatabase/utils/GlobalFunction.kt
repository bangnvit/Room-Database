package com.bangnv.mvvmarchitecturepattern.utils

import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager

object GlobalFunction {
    @JvmStatic
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if currentFocus is not null/null
        activity.currentFocus?.let { view -> // Not null
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        } ?: run {
            // currentFocus is null
            Log.d("GlobalFunction", "No current focus to hide keyboard.")
        }
    }

    @JvmStatic
    fun showSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}