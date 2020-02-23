package com.orioltobar.domain.repositories

import com.orioltobar.commons.Response
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import com.orioltobar.domain.models.movie.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(id: Long): Response<MovieModel, ErrorModel>

    fun getMovieFlow(): Flow<Response<MovieModel, ErrorModel>>

    suspend fun getMovieList(pageId: Int): Response<List<MovieModel>, ErrorModel>

    suspend fun getMovieListByGender(genderId: Int): Response<List<MovieModel>, ErrorModel>

    suspend fun getMovieGenres(): Response<List<MovieGenreDetailModel>, ErrorModel>
}