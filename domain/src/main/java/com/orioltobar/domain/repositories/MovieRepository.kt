package com.orioltobar.domain.repositories

import com.orioltobar.commons.Response
import com.orioltobar.domain.models.movie.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(id: Long): Response<MovieModel>

    fun getMovieFlow(): Flow<Response<MovieModel>>
}