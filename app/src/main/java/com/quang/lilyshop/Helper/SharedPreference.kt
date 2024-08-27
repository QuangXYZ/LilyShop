package com.quang.lilyshop.Helper
import android.content.Context

class MySharedPreferences(private val context: Context) {
    fun putBooleanValue(key: String?, value: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getIntValue(key: String?): Int {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0)
    }

    fun putIntValue(key: String?, value: Int) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun clearValue(key: String?) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun getBooleanValue(key: String?): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun putStringValue(key: String?, value: String?) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringValue(key: String?): String? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")
    }

    companion object {
        private const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
    }
}