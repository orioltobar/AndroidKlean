package com.orioltobar.diskdatasource.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDbModel(
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "tittle")
    val tittle: String,

    @ColumnInfo(name = "timestamp")
    val timeStamp: Long
)