package com.example.reaction.logik

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val PREFERENCE_NAME = "UserPrefs"
    private const val KEY_SHOW_ELEMENT = "showElement"


    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setShowElement(context: Context, show: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_SHOW_ELEMENT, show)
        editor.apply()
    }

    fun shouldShowElement(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_SHOW_ELEMENT, false)
    }


}
