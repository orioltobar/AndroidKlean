package com.orioltobar.diskdatasource.dao

import androidx.room.*
import com.orioltobar.diskdatasource.models.MovieGenreDbModel
import com.orioltobar.diskdatasource.models.MovieGenreListDbModel

@Dao
interface MovieGenreDao {

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<MovieGenreDbModel>

    @Query("SELECT * FROM genres WHERE id = :genreId")
    suspend fun getGenre(genreId: Int): MovieGenreDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setGenre(movie: MovieGenreDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenreListPage(page: MovieGenreListDbModel)

    @Query("SELECT * FROM genre_pages WHERE :genreId = genreId")
    suspend fun getGenreListPage(genreId: Int): MovieGenreListDbModel?

    @Delete
    suspend fun deleteGenre(user: MovieGenreDbModel)
}