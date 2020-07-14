package com.orioltobar.diskdatasource

import androidx.room.TypeConverter

/**
 * Utility class used to convert data that SQL don't know how to serialize.
 */
class Converters {
    @TypeConverter
    fun fromIntList(from: List<Int>): String = from.joinToString(separator = ",")

    @TypeConverter
    fun toIntList(from: String): List<Int> = from.takeIf { it.isNotEmpty() }?.split(",")?.map { it.toInt() } ?: emptyList()

    @TypeConverter
    fun fromLongList(from: List<Long>): String = from.joinToString(separator = ",")

    @TypeConverter
    fun toLongList(from: String): List<Long> = from.takeIf { it.isNotEmpty() }?.split(",")?.map { it.toLong() } ?: emptyList()
}