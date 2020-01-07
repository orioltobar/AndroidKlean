package com.orioltobar.domain.repositories

import com.orioltobar.commons.CoroutineResponse
import com.orioltobar.domain.models.movie.MovieModel

interface MovieRepository {

    suspend fun getMovie(id: Long): CoroutineResponse<MovieModel>
}