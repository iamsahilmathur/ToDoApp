package com.example.todo.core.utilities


import android.content.Context
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.presentation.dashboard.EventAdapter


fun <T : Any> Fragment.autoCleaned(initializer: (() -> T)? = null): AutoCleanedValue<T> {
    return AutoCleanedValue(this, initializer)
}

fun View.isVisible(flag: Boolean) {
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


fun View.showMenu(context: Context, titleFinder: EventAdapter.MyViewHolder.ITitleFinder): String {
    var sTitle = ""
    val popupMenu = PopupMenu(context, this)
    popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
    popupMenu.setOnMenuItemClickListener {
        titleFinder.getAction(it.title.toString())
        return@setOnMenuItemClickListener true
    }
    popupMenu.show()

    return sTitle
}


