package com.orioltobar.networkdatasource.api.data

import com.orioltobar.commons.CoroutineResponse
import com.orioltobar.commons.mapResponse
import com.orioltobar.data.datasources.MovieDataSource
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.networkdatasource.api.mappers.MovieMapper
import com.orioltobar.networkdatasource.utils.safeApiCall
import retrofit2.Retrofit
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    client: Retrofit,
    private val movieMapper: MovieMapper
) : MovieDataSource {

    private val service by lazy { client.create(MovieService::class.java) }

    override suspend fun getMovie(id: Long): CoroutineResponse<MovieModel> {
        return safeApiCall { service.getMovie(id) }.mapResponse(movieMapper::map)
    }

}