package com.orioltobar.domain.repositories

import com.orioltobar.commons.Response
import com.orioltobar.commons.error.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(id: Long): Response<ErrorModel, MovieModel>

    fun getMovieFlow(): Flow<Response<ErrorModel, MovieModel>>

    suspend fun getMovieList(pageId: Int): Response<ErrorModel, List<MovieModel>>

    suspend fun getMovieListByGenre(genreId: Int): Response<ErrorModel, List<MovieModel>>

    suspend fun getMovieGenres(): Response<ErrorModel, List<MovieGenreDetailModel>>
}