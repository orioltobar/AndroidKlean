package com.orioltobar.commons

import java.util.*
import java.util.concurrent.TimeUnit

object Cache {

    /**
     * Simple cache system. It checks the current time and the [cacheTimestamp] (shared preferences
     * value) to see whether is lower than the cache default time or not.
     *
     * @return True if the value is not expired, false otherwise.
     */
    fun checkTimestampCache(cacheTimestamp: Long): Boolean =
        Date().time -  cacheTimestamp < TimeUnit.MINUTES.toMillis(Constants.CACHE_TIME_MINUTES)
}
