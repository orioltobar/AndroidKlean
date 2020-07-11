package com.orioltobar.data.datasources

interface CacheDataSource {

    fun saveCacheTimestamp(timestamp: Long)

    fun getCacheTimestamp(): Long
}