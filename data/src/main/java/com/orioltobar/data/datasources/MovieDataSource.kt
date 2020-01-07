package com.orioltobar.data.datasources

import com.orioltobar.commons.CoroutineResponse
import com.orioltobar.domain.models.movie.MovieModel

interface MovieDataSource {

    suspend fun getMovie(id: Long): CoroutineResponse<MovieModel>
}