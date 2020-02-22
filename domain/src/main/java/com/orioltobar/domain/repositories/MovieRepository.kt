package com.orioltobar.domain.repositories

import com.orioltobar.commons.Response
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(id: Long): Response<MovieModel, ErrorModel>

    fun getMovieFlow(): Flow<Response<MovieModel, ErrorModel>>
}