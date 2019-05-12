package com.example.vinod.radiusassignment.utils

import android.content.Context
import android.content.SharedPreferences

import com.example.vinod.radiusassignment.view.BaseApplication

class SharedPreference private constructor(context: Context) {
  private val sharedPreferences: SharedPreferences

  companion object {

    private val PREFS_NAME = "LoginPrefs"
    private var instance: SharedPreference? = null

    fun getInstance(): SharedPreference {
      if (instance == null) {
        instance = SharedPreference(BaseApplication.instance.applicationContext)
      }
      return instance as SharedPreference
    }
  }

  init {
    sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
  }

  fun putSharedPref(key: String, value: String) {
    sharedPreferences.edit().putString(key, value).apply()
  }

  fun putSharedPrefInt(key: String, value: Int) {
    sharedPreferences.edit().putInt(key, value).apply()
  }

  fun putSharedPrefLong(key: String, value: Long) {
    sharedPreferences.edit().putLong(key, value).apply()
  }

  fun putSharedPrefBoolean(key: String, value: Boolean?) {
    sharedPreferences.edit().putBoolean(key, value!!).apply()
  }

  fun getSharedPref(key: String): String? {
    return sharedPreferences.getString(key, null)
  }

  fun getSharedPrefInt(key: String): Int {
    return sharedPreferences.getInt(key, 0)
  }

  fun getSharedPrefLong(key: String): Long? {
    return sharedPreferences.getLong(key, 0)
  }

  fun getSharedPrefBoolean(key: String): Boolean? {
    return sharedPreferences.getBoolean(key, false)
  }

  fun containsKey(key: String): Boolean {
    return sharedPreferences.contains(key)
  }

  fun deleteKey(key: String) {
    val editor = sharedPreferences.edit()
    editor.remove(key)
    editor.apply()
  }
}
