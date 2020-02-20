package com.orioltobar.diskdatasource

import androidx.room.TypeConverter
import java.util.*

/**
 * Utility class used to convert data that SQL don't know how to serialize.
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}