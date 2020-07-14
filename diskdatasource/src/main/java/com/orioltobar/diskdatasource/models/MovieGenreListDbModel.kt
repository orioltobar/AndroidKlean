package com.orioltobar.diskdatasource.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_pages")
data class MovieGenreListDbModel(
    @PrimaryKey
    val genreId: Int,
    val page: Int,
    val movies: List<Long>,
    val timestamp: Long
)