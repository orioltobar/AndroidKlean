package com.orioltobar.networkdatasource.api.data

import com.orioltobar.commons.CoroutineResponse
import com.orioltobar.commons.mapResponse
import com.orioltobar.data.datasources.MovieDataSource
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.networkdatasource.api.mappers.MovieMapper
import com.orioltobar.networkdatasource.utils.safeApiCall
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieMapper: MovieMapper
) : MovieDataSource {

    override suspend fun getMovie(id: Long): CoroutineResponse<MovieModel> {
        return safeApiCall { movieService.getMovie(id) }.mapResponse(movieMapper::map)
    }
}