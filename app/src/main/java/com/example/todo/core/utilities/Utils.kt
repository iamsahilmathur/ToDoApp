package com.example.todo.core.utilities

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar


object Utils {

    fun showSnackbar(view: View, msg: String) {
        val snackbar = Snackbar.make(
            view, msg, Snackbar.LENGTH_LONG
        )
        snackbar.view.layoutParams =
            (snackbar.view.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.TOP
            }
        snackbar.show()
    }

}