package com.orioltobar.diskdatasource.dao

import androidx.room.*
import com.orioltobar.diskdatasource.models.MovieDbModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MovieDbModel>

    @Query("SELECT * FROM movies WHERE mainGenreId == :genreId")
    suspend fun getMoviesByGenre(genreId: Int): List<MovieDbModel>

    @Query("SELECT * FROM movies WHERE id == :id")
    suspend fun getMovie(id: Long): MovieDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithTimestamp(movie: MovieDbModel)

    @Update
    suspend fun updateMovie(movie: MovieDbModel)

    @Delete
    suspend fun deleteMovie(user: MovieDbModel)
}