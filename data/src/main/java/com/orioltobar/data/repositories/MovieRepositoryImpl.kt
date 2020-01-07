package com.orioltobar.data.repositories

import com.orioltobar.commons.CoroutineResponse
import com.orioltobar.data.datasources.MovieDataSource
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.domain.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val dataSource: MovieDataSource) :
    MovieRepository {

    override suspend fun getMovie(id: Long): CoroutineResponse<MovieModel> =
        dataSource.getMovie(id)
}