package com.orioltobar.data.datasources

import com.orioltobar.commons.Response
import com.orioltobar.domain.models.movie.MovieModel

interface MovieDataSource {

    suspend fun getMovie(id: Long): Response<MovieModel>
}