package com.orioltobar.diskdatasource.data

import android.content.SharedPreferences
import com.orioltobar.data.datasources.CacheDataSource
import com.orioltobar.diskdatasource.SharedPreferencesBehaviour
import com.orioltobar.diskdatasource.extensions.put
import java.util.*
import javax.inject.Inject

class CacheDataSourceImpl @Inject constructor(
    override val preferences: SharedPreferences
) : CacheDataSource, SharedPreferencesBehaviour {

    override fun saveCacheTimestamp(timestamp: Long) {
        preferences.put(CACHE_TIMESTAMP, timestamp)
    }

    override fun getCacheTimestamp(): Long {
        val timeStamp = preferences.getLong(CACHE_TIMESTAMP, -1L)
        // If timestamp is -1L, a new timestamp is generated.
        return if (timeStamp == -1L) {
            val newTimeStamp = Date().time
            put(CACHE_TIMESTAMP, newTimeStamp)
            newTimeStamp
        } else timeStamp
    }

    companion object {
        private const val CACHE_TIMESTAMP = "cache_timestamp"
    }
}