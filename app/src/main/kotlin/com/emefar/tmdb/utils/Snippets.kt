package com.emefar.tmdb.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

import java.util.Objects


@Suppress("unused")
object Snippets {

    fun dpToPixels(dp: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale).toInt()
    }


    fun hideKeyboard(activity: Activity, showOrHide: Boolean) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            if (showOrHide) {
                inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    0
                )
                activity.currentFocus!!.clearFocus()
            } else {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            }

        }
    }

    fun showKeyboard(activity: Activity) {
        (Objects.requireNonNull(activity.getSystemService(Activity.INPUT_METHOD_SERVICE)) as InputMethodManager)
            .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


}
