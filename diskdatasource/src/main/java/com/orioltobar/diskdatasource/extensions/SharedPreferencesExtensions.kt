package com.orioltobar.diskdatasource.extensions

import android.content.SharedPreferences

fun SharedPreferences.put(key: String, value: Boolean?) =
    value?.let { edit().putBoolean(key, it).apply() } ?: remove(key)

fun SharedPreferences.put(key: String, value: Int?) =
    value?.let { edit().putInt(key, it).apply() } ?: remove(key)

fun SharedPreferences.put(key: String, value: Long?) =
    value?.let { edit().putLong(key, it).apply() } ?: remove(key)

fun SharedPreferences.put(key: String, value: String?) =
    value?.let { edit().putString(key, value).apply() } ?: remove(key)

fun SharedPreferences.put(key: String, values: Set<String>?) =
    values?.let { edit().putStringSet(key, it).apply() } ?: remove(key)

fun SharedPreferences.remove(key: String) = edit().remove(key).apply()

fun SharedPreferences.clear() = edit().clear().apply()