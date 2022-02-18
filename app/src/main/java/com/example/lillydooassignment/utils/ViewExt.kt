package com.example.lillydooassignment.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun SwipeRefreshLayout.makeRefreshVisible() {
    isRefreshing = true
}

fun SwipeRefreshLayout.makeRefreshGone() {
    isRefreshing = false
}

fun Context.showToast(@StringRes message : Int) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message : String) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes message : Int) {
    requireContext().showToast(message)
}

fun Fragment.showToast(message : String) {
    requireContext().showToast(message)
}