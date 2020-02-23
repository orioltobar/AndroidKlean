package com.orioltobar.diskdatasource.dao

import androidx.room.*
import com.orioltobar.diskdatasource.models.MovieGenreDbModel

@Dao
interface MovieGenreDao {

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<MovieGenreDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setGenre(movie: MovieGenreDbModel)

    @Delete
    suspend fun deleteGenre(user: MovieGenreDbModel)
}