package com.orioltobar.diskdatasource

import android.content.SharedPreferences
import com.orioltobar.diskdatasource.extensions.clear
import com.orioltobar.diskdatasource.extensions.put
import com.orioltobar.diskdatasource.extensions.remove

interface SharedPreferencesBehaviour {

    val preferences: SharedPreferences

    fun put(key: String, value: Int?) = preferences.put(key, value)

    fun put(key: String, value: Long?) = preferences.put(key, value)

    fun put(key: String, value: String?) = preferences.put(key, value)

    fun put(key: String, value: Boolean) = preferences.put(key, value)

    fun get(key: String, defaultValue: Int): Int = preferences.getInt(key, defaultValue)

    fun get(key: String, defaultValue: Long): Long = preferences.getLong(key, defaultValue)

    fun get(key: String, defaultValue: String): String =
        preferences.getString(key, defaultValue) ?: ""

    fun get(key: String, defaultValue: Boolean): Boolean = preferences.getBoolean(key, defaultValue)

    fun removeKey(key: String) = preferences.remove(key)

    fun clear() = preferences.clear()

    fun contains(key: String) = preferences.contains(key)

    fun getAll(): MutableMap<String, *> = preferences.all
}